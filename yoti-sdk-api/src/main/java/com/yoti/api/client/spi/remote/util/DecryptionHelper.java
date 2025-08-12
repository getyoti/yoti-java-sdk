package com.yoti.api.client.spi.remote.util;

import static javax.crypto.Cipher.DECRYPT_MODE;

import static com.yoti.api.client.spi.remote.call.YotiConstants.ASYMMETRIC_CIPHER;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import javax.crypto.Cipher;

import com.yoti.api.client.ProfileException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DecryptionHelper {

    public static byte[] decryptAsymmetric(byte[] source, PrivateKey key) throws ProfileException {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(DECRYPT_MODE, key);
            return cipher.doFinal(source);
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Error decrypting data", gse);
        } catch (IllegalArgumentException iae) {
            throw new ProfileException("Base64 encoding error", iae);
        }
    }

}
