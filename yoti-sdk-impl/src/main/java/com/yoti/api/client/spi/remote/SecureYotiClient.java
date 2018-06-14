package com.yoti.api.client.spi.remote;

import static javax.crypto.Cipher.DECRYPT_MODE;

import static com.yoti.api.client.spi.remote.call.YotiConstants.ASYMMETRIC_CIPHER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.BOUNCY_CASTLE_PROVIDER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SYMMETRIC_CIPHER;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.ActivityFailureException;
import com.yoti.api.client.AmlException;
import com.yoti.api.client.Attribute;
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
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.AttributeListProto.AttributeList;
import com.yoti.api.client.spi.remote.proto.EncryptedDataProto.EncryptedData;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * YotiClient talking to the Yoti Connect API remotely.
 */
final class SecureYotiClient implements YotiClient {

    private static final Logger LOG = LoggerFactory.getLogger(SecureYotiClient.class);
    private static final String RFC3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private final String appId;
    private final KeyPair keyPair;
    private final ProfileService profileService;
    private final RemoteAmlService remoteAmlService;
    private final AttributeConverter attributeConverter;

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    SecureYotiClient(String applicationId,
                     KeyPairSource kpSource,
                     ProfileService profileService,
                     RemoteAmlService remoteAmlService,
                     AttributeConverter attributeConverter) throws InitialisationException {
        this.appId = notNull(applicationId, "Application id");
        this.keyPair = loadKeyPair(notNull(kpSource, "Key pair source"));
        this.profileService = notNull(profileService, "Profile service");
        this.remoteAmlService = notNull(remoteAmlService, "Aml service");
        this.attributeConverter = notNull(attributeConverter, "Attribute Converter");
    }

    @Override
    public ActivityDetails getActivityDetails(String encryptedConnectToken) throws ProfileException {
        Receipt receipt = getReceipt(encryptedConnectToken, keyPair);
        return buildActivityDetails(receipt, keyPair.getPrivate());
    }

    @Override
    public AmlResult performAmlCheck(AmlProfile amlProfile) throws AmlException {
        LOG.debug("Performing aml check...");
        return remoteAmlService.performCheck(keyPair, appId, amlProfile);
    }

    private Receipt getReceipt(String encryptedConnectToken, KeyPair keyPair) throws ProfileException {
        LOG.debug("Decrypting connect token: '{}'", encryptedConnectToken);
        String connectToken = decryptConnectToken(encryptedConnectToken, keyPair.getPrivate());
        LOG.debug("Connect token decrypted: '{}'", connectToken);
        Receipt receipt = profileService.getReceipt(keyPair, appId, connectToken);
        if (receipt == null) {
            throw new ProfileException("No receipt for '" + connectToken + "' was found");
        }
        return receipt;
    }

    private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
        try {
            LOG.debug("Loading key pair from '{}'", kpSource);
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

    private ActivityDetails buildActivityDetails(Receipt receipt, PrivateKey privateKey) throws ProfileException {
        validateReceipt(receipt);
        Key secretKey = new SecretKeySpec(decrypt(receipt.getWrappedReceiptKey(), privateKey), SYMMETRIC_CIPHER);
        Profile userProfile = createProfile(receipt.getOtherPartyProfile(), secretKey);
        Profile applicationProfile = createProfile(receipt.getProfile(), secretKey);
        return createActivityDetails(userProfile, applicationProfile, receipt);
    }

    private void validateReceipt(Receipt receipt) throws ActivityFailureException {
        if (receipt.getOutcome() == null || !receipt.getOutcome().isSuccessful()) {
            throw new ActivityFailureException("Sharing activity unsuccessful for " + receipt.getDisplayReceiptId());
        }
    }

    private Profile createProfile(byte[] profileBytes, Key secretKey) throws ProfileException {
        List<Attribute<?>> attributeList = new ArrayList<>();
        if (profileBytes != null && profileBytes.length > 0) {
            EncryptedData encryptedData = parseProfileContent(profileBytes);
            byte[] profileData = decrypt(encryptedData.getCipherText(), secretKey, encryptedData.getIv());
            attributeList = parseProfile(profileData);
        }
        return new SimpleProfile(attributeList);
    }

    private EncryptedData parseProfileContent(byte[] profileContent) throws ProfileException {
        try {
            return EncryptedData.parseFrom(profileContent);
        } catch (InvalidProtocolBufferException e) {
            throw new ProfileException("Cannot decode profile", e);
        }
    }

    private List<Attribute<?>> parseProfile(byte[] profileData) throws ProfileException {
        try {
            AttributeList message = AttributeList.parseFrom(profileData);
            List<Attribute<?>> attributeList = parseAttributes(message);
            LOG.debug("{} attribute(s) parsed", attributeList.size());
            return attributeList;
        } catch (InvalidProtocolBufferException e) {
            throw new ProfileException("Cannot parse profile data", e);
        }
    }

    private List<Attribute<?>> parseAttributes(AttributeList message) {
        List<Attribute<?>> parsedAttributes = new ArrayList<>();
        for (AttrProto.Attribute attribute : message.getAttributesList()) {
            try {
                parsedAttributes.add(attributeConverter.convertAttribute(attribute));
            } catch (IOException | ParseException e) {
                LOG.warn("Cannot parse value for attribute '{}'", attribute.getName());
            }
        }
        return parsedAttributes;
    }

    private ActivityDetails createActivityDetails(Profile userProfile, Profile applicationProfile, Receipt receipt) throws ProfileException {
        try {
            byte[] rmi = receipt.getRememberMeId();
            String rememberMeId = (rmi == null) ? null : new String(Base64.getEncoder().encode(rmi), DEFAULT_CHARSET);
            SimpleDateFormat format = new SimpleDateFormat(RFC3339_PATTERN);
            Date timestamp = format.parse(receipt.getTimestamp());
            return new SimpleActivityDetails(rememberMeId, userProfile, applicationProfile, timestamp, receipt.getReceiptId());
        } catch (UnsupportedEncodingException e) {
            throw new ProfileException("Cannot parse user ID", e);
        } catch (ParseException e) {
            throw new ProfileException("Cannot parse timestamp", e);
        }
    }

    private byte[] decrypt(ByteString source, Key key, ByteString initVector) throws ProfileException {
        if (initVector == null || initVector.size() == 0) {
            throw new ProfileException("Receipt key IV must not be null.");
        }
        try {
            Cipher cipher = Cipher.getInstance(SYMMETRIC_CIPHER, BOUNCY_CASTLE_PROVIDER);
            cipher.init(DECRYPT_MODE, key, new IvParameterSpec(initVector.toByteArray()));
            return cipher.doFinal(source.toByteArray());
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Error decrypting data", gse);
        } catch (IllegalArgumentException iae) {
            throw new ProfileException("Base64 encoding error", iae);
        }
    }

    private byte[] decrypt(byte[] source, PrivateKey key) throws ProfileException {
        try {
            Cipher cipher = Cipher.getInstance(ASYMMETRIC_CIPHER, BOUNCY_CASTLE_PROVIDER);
            cipher.init(DECRYPT_MODE, key);
            return cipher.doFinal(source);
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Error decrypting data", gse);
        } catch (IllegalArgumentException iae) {
            throw new ProfileException("Base64 encoding error", iae);
        }
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

        private KeyPair findKeyPair(PEMParser reader) throws IOException {
            for (Object o; (o = reader.readObject()) != null; ) {
                if (o instanceof PEMKeyPair) {
                    return new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) o);
                }
            }
            return null;
        }

    }

}
