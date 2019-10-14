package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.SYMMETRIC_CIPHER;
import static javax.crypto.Cipher.DECRYPT_MODE;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.proto.EncryptedDataProto;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.security.Key;

public class EncryptedDataReader {

    public byte[] decryptBytes(byte[] encryptedBytes, Key secretKey) throws ProfileException {
        EncryptedDataProto.EncryptedData encryptedData = parseEncryptedContent(encryptedBytes);
        return decrypt(encryptedData, secretKey);
    }

    private EncryptedDataProto.EncryptedData parseEncryptedContent(byte[] encryptedBytes) throws ProfileException {
        try {
            return EncryptedDataProto.EncryptedData.parseFrom(encryptedBytes);
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
