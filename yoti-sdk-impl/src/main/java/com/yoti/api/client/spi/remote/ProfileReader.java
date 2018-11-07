package com.yoti.api.client.spi.remote;

import static javax.crypto.Cipher.DECRYPT_MODE;

import static com.yoti.api.client.spi.remote.call.YotiConstants.SYMMETRIC_CIPHER;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.proto.EncryptedDataProto;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

class ProfileReader {

    private final AttributeListConverter attributeListConverter;

    private ProfileReader(AttributeListConverter attributeListConverter) {
        this.attributeListConverter = attributeListConverter;
    }

    static ProfileReader newInstance() {
        return new ProfileReader(AttributeListConverter.newInstance());
    }

    Profile read(byte[] encryptedProfileBytes, Key secretKey) throws ProfileException {
        List<Attribute<?>> attributeList = new ArrayList<>();
        if (encryptedProfileBytes != null && encryptedProfileBytes.length > 0) {
            byte[] profileData = decryptProfile(encryptedProfileBytes, secretKey);
            attributeList = attributeListConverter.parseAttributeList(profileData);
        }
        return new SimpleProfile(attributeList);
    }

    private byte[] decryptProfile(byte[] encryptedProfileBytes, Key secretKey) throws ProfileException {
        EncryptedDataProto.EncryptedData encryptedData = parseEncryptedContent(encryptedProfileBytes);
        return decrypt(encryptedData, secretKey);
    }

    private EncryptedDataProto.EncryptedData parseEncryptedContent(byte[] encryptedProfileBytes) throws ProfileException {
        try {
            return EncryptedDataProto.EncryptedData.parseFrom(encryptedProfileBytes);
        } catch (InvalidProtocolBufferException e) {
            throw new ProfileException("Cannot decode profile", e);
        }
    }

    private byte[] decrypt(EncryptedDataProto.EncryptedData encryptedData, Key secretKey) throws ProfileException {
        ByteString initVector = encryptedData.getIv();
        if (initVector == null || initVector.size() == 0) {
            throw new ProfileException("Receipt key IV must not be null.");
        }
        try {
            Cipher cipher = Cipher.getInstance(SYMMETRIC_CIPHER, BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(DECRYPT_MODE, secretKey, new IvParameterSpec(initVector.toByteArray()));
            return cipher.doFinal(encryptedData.getCipherText().toByteArray());
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Error decrypting data", gse);
        } catch (IllegalArgumentException iae) {
            throw new ProfileException("Base64 encoding error", iae);
        }
    }

}
