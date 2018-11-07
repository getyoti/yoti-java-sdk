package com.yoti.api.client.spi.remote.util;

import static com.yoti.api.client.spi.remote.call.YotiConstants.ASYMMETRIC_CIPHER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SIGNATURE_ALGORITHM;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SYMMETRIC_CIPHER;

import com.yoti.api.client.spi.remote.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.junit.Assert;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;

public class CryptoUtil {

    private static final String SYMMETRIC_KEY_ALGO = "AES";
    private static final int SYMMETRIC_LENGTH = 256;

    public static final String INVALID_KEY_PAIR_PEM = "-----BEGIN RSA PRIVATE KEY-----\n"
            + "qp9qiAaZZpL4RJKOA9x3uJxC3XVIRa+8TpvWmpUnFJJZMotE/W6jjPvOLiu4V27m\n"
            + "H0i1CNy5Icjx7g3Wo2Pj2AUEOqV+bB1we2VSbNhPpJHmN2azmOMRuc7SMyXukWy5\n"
            + "EeS2ZlwD2cfm3jJdTmWysV6+D4t02y3DYckjRe3RA/5pOYYg7+qgL/fjtb8eUOx3\n"
            + "08fwzlS0dXwuYegX0aJuUd4KOu/drDzg2KDHDKlUmuPgHTKwlKX/mgjaXbldB9ER\n"
            + "pTyq68OtJ37TElNgTl/rashfbEaC3LbQtn3lFQIDAQABAoIBADS3XSmhcyvN7VQl\n"
            + "XLYQZsxhlTOTqrx2Qb4dGTpy5KfxdzEr3TkrpE50sEexifXpdCLFSqKo/8SfSl0I\n"
            + "g4rPx3NZPgNwZ+Q6hCWtr2q4OxIIYpwSLZLn+nGWtwsf57FyL61lRvZJJ42D0X8k\n"
            + "kNQBPn9PoplzgddMCZz/IFBKva08WDlib6h9eBMUXxw94/kEpp1WBhWreq4trADg\n"
            + "l8l4BHTheT259PmgDq8L83irGjxYbuMrlNrOigXfesgbyX8UBVVkTEqHUWNnNRAR\n" //
            + "-----END RSA PRIVATE KEY-----";

    public static final String KEY_PAIR_PEM = "-----BEGIN RSA PRIVATE KEY-----\n"
            + "MIIEogIBAAKCAQEAvTGVVGqq2jv9K1BwT8+7GAURqoZOT8gfk2vIT3YKf3ZEB2OG\n"
            + "qp9qiAaZZpL4RJKOA9x3uJxC3XVIRa+8TpvWmpUnFJJZMotE/W6jjPvOLiu4V27m\n"
            + "H0i1CNy5Icjx7g3Wo2Pj2AUEOqV+bB1we2VSbNhPpJHmN2azmOMRuc7SMyXukWy5\n"
            + "EeS2ZlwD2cfm3jJdTmWysV6+D4t02y3DYckjRe3RA/5pOYYg7+qgL/fjtb8eUOx3\n"
            + "08fwzlS0dXwuYegX0aJuUd4KOu/drDzg2KDHDKlUmuPgHTKwlKX/mgjaXbldB9ER\n"
            + "pTyq68OtJ37TElNgTl/rashfbEaC3LbQtn3lFQIDAQABAoIBADS3XSmhcyvN7VQl\n"
            + "XLYQZsxhlTOTqrx2Qb4dGTpy5KfxdzEr3TkrpE50sEexifXpdCLFSqKo/8SfSl0I\n"
            + "g4rPx3NZPgNwZ+Q6hCWtr2q4OxIIYpwSLZLn+nGWtwsf57FyL61lRvZJJ42D0X8k\n"
            + "kNQBPn9PoplzgddMCZz/IFBKva08WDlib6h9eBMUXxw94/kEpp1WBhWreq4trADg\n"
            + "l8l4BHTheT259PmgDq8L83irGjxYbuMrlNrOigXfesgbyX8UBVVkTEqHUWNnNRAR\n"
            + "XONm+75GJT8r51ijdpHfEnpy54bNTXT7KHVMaHmPSQIazylvQJQew0WVZ0a9VwvF\n"
            + "QZPOngECgYEA9AngQ+GCJW9vC4UCtIpuWZajCw+KNXlw4rfpXJuajnm3O+znfO+q\n"
            + "FQn8IA5u1Sw1DimvdLACSavCax0p6vvJtAxjTgPtID6OnBJ3cD0rjqAcqEbZ29Xu\n"
            + "YeaAg0dnSRfb5wkpbk0EtK2RkFocwdM/ql1s8WkrFkqs681zRqGLs5UCgYEAxneH\n"
            + "nuHkIq8O69h+CI1RmNZpLhQBspseHXbhwTzQIvqzdiIwlPTR9mfZsGrnCokb/0CC\n"
            + "BvYih+GLZN62iEaf+aCUA+662BZrNAMwEY/k4Ris+sKFNT0tBNoPDOgqdnygH9gm\n"
            + "coKdIDO9sPL0144U/Hq1YsdwuK2SDQcLY3ydC4ECgYA71QALJIsIKp4LMP1MznPn\n"
            + "uysWVyUHn1KyA21Pq0blj6oBI0BOPWRx7BTIt0EtOr13T3kZHt4wuc/c+zV/y2PU\n"
            + "pQTj58qHkU7drRljh1vaiB7+kwBvCbB8iEsR5LvKC/N6XaCuzmtM8REzVySd0PFX\n"
            + "D7jaJ3LM8FodJi4RLyJVUQKBgEy80teACDHQ9jgC0ViFK9Oos6p5Wd6xU4eY+9k3\n"
            + "plKgFNvMhHRT5QsdRHKOIx9TvFuJmb0PVnKrprYt1u4CQMDIcfLDT8NVh8XopaFk\n"
            + "vd67J8cdh1v6d3m0xrT639BIh7FIZjVIg3B8ERBmIH1oFn05BQFYlCEUG7Cl1KV2\n"
            + "/VIBAoGAYin6eHFtBXpPRMddTt4lLOFtpBb9gGp9DxKGAsH0pdfQYgZHz0xKtxrq\n"
            + "6l+d2M9sRhtisxw8YevPFWEVtcUWxz+qESM+wzUMrmWSuwwxXyVgjR4rC8untfPM\n"
            + "laDBX8dDBwBQ3i0FhvuGR/LEjHWr9hj00faKROHOLFim6wbRIkg=\n" //
            + "-----END RSA PRIVATE KEY-----";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static EncryptionResult encryptSymmetric(byte[] data, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(SYMMETRIC_CIPHER, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return new EncryptionResult(cipher.doFinal(data), cipher.getIV());
    }

    public static byte[] encryptAsymmetric(byte[] data, Key key) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ASYMMETRIC_CIPHER, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    public static KeyPair generateKeyPairFrom(String keyPairString) throws IOException {
        KeyPair keyPair = null;
        PEMParser reader = new PEMParser(
                new BufferedReader(new InputStreamReader(new ByteArrayInputStream(keyPairString.getBytes()))));

        for (Object o; (o = reader.readObject()) != null;) {
            if (o instanceof PEMKeyPair) {
                keyPair = new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) o);
                break;
            }
        }
        reader.close();
        return keyPair;
    }

    public static Key generateSymmetricKey() throws GeneralSecurityException {
        KeyGenerator keyGen = KeyGenerator.getInstance(SYMMETRIC_KEY_ALGO, BouncyCastleProvider.PROVIDER_NAME);
        keyGen.init(SYMMETRIC_LENGTH);
        return keyGen.generateKey();
    }

    public static void verifyMessage(byte[] message, PublicKey publicKey, byte[] receivedSignature) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        signature.initVerify(publicKey);
        signature.update(message);
        Assert.assertTrue(signature.verify(receivedSignature));
    }

    public static String base64(byte[] source) {
        return Base64.getEncoder().encodeToString(source);
    }

    public static String base64Url(byte[] source) {
        return Base64.getUrlEncoder().encodeToString(source);
    }

    public static class EncryptionResult {
        public final byte[] data;
        public final byte[] iv;

        EncryptionResult(byte[] data, byte[] iv) {
            this.data = data;
            this.iv = iv;
        }
    }
}
