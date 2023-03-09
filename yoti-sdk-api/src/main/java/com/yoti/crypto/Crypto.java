package com.yoti.crypto;

import java.security.Key;
import java.security.PrivateKey;
import java.util.function.Supplier;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class Crypto {

    private static final String DECRYPT_ERROR_TEMPLATE = "Failed to decrypt message using '%s' transformation";

    public static byte[] decrypt(byte[] msg, PrivateKey key, CipherType transformation) {
        return wrapOnException(
                () -> {
                    try {
                        return doCipher(msg, key, transformation);
                    } catch (Exception ex) {
                        throw new CryptoException(ex);
                    }
                },
                String.format(DECRYPT_ERROR_TEMPLATE, transformation.value())
        );
    }

    private static byte[] doCipher(byte[] msg, Key key, CipherType transformation) throws Exception {
        Cipher cipher = transformation.getInstance();
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(msg);
    }

    public static byte[] decrypt(byte[] msg, byte[] key, IvParameterSpec iv, CipherType transformation) {
        return decrypt(msg, new SecretKeySpec(key, Algorithm.AES.value()), iv, transformation);
    }

    public static byte[] decrypt(byte[] msg, SecretKeySpec key, IvParameterSpec iv, CipherType transformation) {
        return wrapOnException(
                () -> {
                    try {
                        return doCipher(msg, key, iv, transformation);
                    } catch (Exception ex) {
                        throw new CryptoException(ex);
                    }
                },
                String.format(DECRYPT_ERROR_TEMPLATE, transformation.value())
        );
    }

    private static byte[] doCipher(byte[] msg, SecretKeySpec key, IvParameterSpec iv, CipherType transformation)
            throws Exception {
        Cipher cipher = transformation.getInstance();
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(msg);
    }

    private static <T> T wrapOnException(Supplier<T> op, String msg) {
        try {
            return op.get();
        } catch (Exception ex) {
            throw new CryptoException(msg, ex);
        }
    }

}
