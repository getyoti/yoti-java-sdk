package com.yoti.api.client.spi.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.AmlException;
import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.KeyPairSource.StreamVisitor;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.client.aml.AmlProfile;
import com.yoti.api.client.aml.AmlResult;
import com.yoti.api.client.spi.remote.call.ProfileService;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.call.aml.RemoteAmlService;
import com.yoti.api.client.spi.remote.exception.YotiSigningException;
import com.yoti.api.client.spi.remote.proto.AttrProto.Attribute;
import com.yoti.api.client.spi.remote.proto.AttributeListProto.AttributeList;
import com.yoti.api.client.spi.remote.proto.ContentTypeProto.ContentType;
import com.yoti.api.client.spi.remote.proto.EncryptedDataProto.EncryptedData;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static javax.crypto.Cipher.DECRYPT_MODE;

/**
 * YotiClient talking to the Yoti Connect API remotely.
 */
final class SecureYotiClient implements YotiClient {

    private static final Logger LOG = LoggerFactory.getLogger(SecureYotiClient.class);
    private static final String SYMMETRIC_CIPHER = "AES/CBC/PKCS7Padding";
    private static final String ASYMMETRIC_CIPHER = "RSA/NONE/PKCS1Padding";
    private static final String RFC3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private final String appId;
    private final KeyPair keyPair;
    private final ProfileService profileService;
    private final RemoteAmlService remoteAmlService;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    SecureYotiClient(String applicationId,
                     KeyPairSource kpSource,
                     ProfileService profileService,
                     RemoteAmlService remoteAmlService) throws InitialisationException {
        this.appId = notNull(applicationId, "Application id");
        this.keyPair = loadKeyPair(notNull(kpSource, "Key pair source"));
        this.profileService = notNull(profileService, "Profile service");
        this.remoteAmlService = notNull(remoteAmlService, "Aml service");
    }

    @Override
    public ActivityDetails getActivityDetails(String encryptedConnectToken) throws ProfileException {
        Receipt receipt = getReceipt(encryptedConnectToken, keyPair);
        return buildReceipt(receipt, keyPair.getPrivate());
    }

    @Override
    public AmlResult performAmlCheck(AmlProfile amlProfile) throws AmlException {
        LOG.debug("Performing aml check...");

        return remoteAmlService.performCheck(keyPair, appId, amlProfile);


    }

    private Receipt getReceipt(String encryptedConnectToken, KeyPair keyPair) throws ProfileException {
        LOG.debug("Decrypting connect token: {}", encryptedConnectToken);
        String connectToken = decryptConnectToken(encryptedConnectToken, keyPair.getPrivate());
        LOG.debug("Connect token decrypted: {}", connectToken);
        Receipt receipt = profileService.getReceipt(keyPair, appId, connectToken);
        if (receipt == null) {
            throw new ProfileException("No receipt for " + connectToken + " was found");
        }
        return receipt;
    }

    private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
        try {
            LOG.debug("Loading key pair from " + kpSource);
            return kpSource.getFromStream(new KeyStreamVisitor());
        } catch (IOException e) {
            throw new InitialisationException("Cannot load key pair", e);
        }
    }

    private String decryptConnectToken(String encryptedConnectToken, PrivateKey privateKey) throws ProfileException {
        String connectToken = null;
        try {
            byte[] byteValue = Base64.getUrlDecoder().decode(encryptedConnectToken);
            byte[] decryptedToken = decrypt(byteValue, privateKey);
            connectToken = new String(decryptedToken, DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new ProfileException("Cannot decrypt connect token", e);
        }
        return connectToken;
    }

    private ActivityDetails buildReceipt(Receipt receipt, PrivateKey privateKey) throws ProfileException {
        validateReceipt(receipt);
        Key secretKey = parseKey(decrypt(receipt.getWrappedReceiptKey(), privateKey));
        Profile userProfile = createProfile(receipt.getOtherPartyProfile(), secretKey);
        Profile applicationProfile = createProfile(receipt.getProfile(), secretKey);
        return createActivityDetails(userProfile, applicationProfile, receipt);
    }

    private void validateReceipt(Receipt receipt) throws ActivityFailureException {
        if (receipt.getOutcome() == null || !receipt.getOutcome().isSuccessful()) {
            throw new ActivityFailureException("Sharing activity uncuccessful for " + receipt.getDisplayReceiptId());
        }
    }

    private Profile createProfile(byte[] profileBytes, Key secretKey) throws ProfileException {
        Map<String, Object> attributeMap = new HashMap<String, Object>();
        if (profileBytes != null && profileBytes.length > 0) {
            EncryptedData encryptedData = parseProfileContent(profileBytes);
            byte[] profileData = decrypt(encryptedData.getCipherText(), secretKey, encryptedData.getIv());
            attributeMap = parseProfile(profileData);
        }
        return createProfile(attributeMap);
    }

    private EncryptedData parseProfileContent(byte[] profileContent) throws ProfileException {
        try {
            return EncryptedData.parseFrom(profileContent);
        } catch (InvalidProtocolBufferException e) {
            throw new ProfileException("Cannot decode profile", e);
        }
    }

    private Key parseKey(byte[] keyVal) {
        return new SecretKeySpec(keyVal, SYMMETRIC_CIPHER);
    }

    private Map<String, Object> parseProfile(byte[] profileData) throws ProfileException {
        Map<String, Object> attributeMap = null;
        try {
            AttributeList message = AttributeList.parseFrom(profileData);
            attributeMap = mapAttributes(message);
            LOG.debug("{} attribute(s) parsed", attributeMap.size());
        } catch (InvalidProtocolBufferException e) {
            throw new ProfileException("Cannot parse profile data", e);
        }
        return attributeMap;
    }

    private Map<String, Object> mapAttributes(AttributeList message) {
        Map<String, Object> attributeMap = new HashMap<String, Object>();
        for (Attribute attribute : message.getAttributesList()) {
            try {
                Object attributeValue = mapAttribute(attribute);
                if (attributeValue != null) {
                    attributeMap.put(attribute.getName(), attributeValue);
                }
            } catch (IOException e) {
                LOG.info("Cannot decode value for attribute {}", attribute.getName());
            } catch (ParseException e) {
                LOG.info("Cannot parse value for attribute {}", attribute.getName());
            }
        }
        return attributeMap;
    }

    private Object mapAttribute(Attribute attribute) throws ParseException, IOException {
        if (ContentType.STRING.equals(attribute.getContentType())) {
            return attribute.getValue().toString(DEFAULT_CHARSET);
        } else if (ContentType.DATE.equals(attribute.getContentType())) {
            return DateAttributeValue.parseFrom(attribute.getValue().toByteArray());
        } else if (ContentType.JPEG.equals(attribute.getContentType())) {
            return new JpegAttributeValue(attribute.getValue().toByteArray());
        } else if (ContentType.PNG.equals(attribute.getContentType())) {
            return new PngAttributeValue(attribute.getValue().toByteArray());
        } else if (ContentType.JSON.equals(attribute.getContentType())) {
            return JSON_MAPPER.readValue(attribute.getValue().toString(DEFAULT_CHARSET), Map.class);
        }

        LOG.error("Unknown type {} for attribute {}", attribute.getContentType(), attribute.getName());
        return attribute.getValue().toString(DEFAULT_CHARSET);
    }

    private Profile createProfile(Map<String, Object> attributeMap) {
        return new SimpleProfile(attributeMap);
    }

    private ActivityDetails createActivityDetails(Profile userProfile, Profile applicationProfile, Receipt receipt)
            throws ProfileException {
        try {
            byte[] rmi = receipt.getRememberMeId();
            String rememberMeId = (rmi == null) ? null : new String(Base64.getEncoder().encode(rmi), DEFAULT_CHARSET);

            SimpleDateFormat format = new SimpleDateFormat(RFC3339_PATTERN);
            Date timestamp = format.parse(receipt.getTimestamp());

            return new SimpleActivityDetails(rememberMeId, userProfile, applicationProfile, timestamp,
                    receipt.getReceiptId());
        } catch (UnsupportedEncodingException e) {
            throw new ProfileException("Cannot parse user ID", e);
        } catch (ParseException e) {
            throw new ProfileException("Cannot parse timestamp", e);
        }
    }

    private byte[] decrypt(ByteString source, Key key, ByteString initVector) throws ProfileException {
        if (initVector == null) {
            throw new ProfileException("Receipt key IV must not be null.");
        }
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(SYMMETRIC_CIPHER);
            cipher.init(DECRYPT_MODE, key, new IvParameterSpec(initVector.toByteArray()));
            result = cipher.doFinal(source.toByteArray());
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Error decrypting data", gse);
        } catch (IllegalArgumentException iae) {
            throw new ProfileException("Base64 encoding error", iae);
        }
        return result;
    }

    private byte[] decrypt(byte[] source, PrivateKey key) throws ProfileException {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_CIPHER);
            cipher.init(DECRYPT_MODE, key);
            result = cipher.doFinal(source);
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Error decrypting data", gse);
        } catch (IllegalArgumentException iae) {
            throw new ProfileException("Base64 encoding error", iae);
        }
        return result;
    }

    private static class KeyStreamVisitor implements StreamVisitor {

        @Override
        public KeyPair accept(InputStream stream) throws IOException, InitialisationException {
            PEMParser reader = new PEMParser(new BufferedReader(new InputStreamReader(stream, DEFAULT_CHARSET)));
            KeyPair keyPair = findKeyPair(reader);
            if (keyPair == null) {
                throw new InitialisationException("No key pair found in the provided source");
            }
            return keyPair;
        }

        private KeyPair findKeyPair(PEMParser reader) throws IOException, PEMException {
            KeyPair keyPair = null;
            for (Object o = null; (o = reader.readObject()) != null; ) {
                if (o instanceof PEMKeyPair) {
                    keyPair = new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) o);
                    break;
                }
            }
            return keyPair;
        }
    }
}
