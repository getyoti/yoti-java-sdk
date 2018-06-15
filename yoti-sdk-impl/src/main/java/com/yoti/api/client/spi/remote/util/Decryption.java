package com.yoti.api.client.spi.remote.util;

import static javax.crypto.Cipher.DECRYPT_MODE;

import static com.yoti.api.client.spi.remote.call.YotiConstants.ASYMMETRIC_CIPHER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.BOUNCY_CASTLE_PROVIDER;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import javax.crypto.Cipher;

import com.yoti.api.client.ProfileException;

public class Decryption {

    public static byte[] decryptAsymmetric(byte[] source, PrivateKey key) throws ProfileException {
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

}
