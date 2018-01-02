package com.yoti.api.client.spi.remote.call.factory;

import com.yoti.api.client.ProfileException;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;

public class SignatureFactory {

    private static final String BOUNCY_CASTLE_PROVIDER = "BC";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public byte[] create(byte[] message, PrivateKey privateKey) throws ProfileException {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
            signature.initSign(privateKey, new SecureRandom());
            signature.update(message);
            return signature.sign();
        } catch (GeneralSecurityException gse) {
            throw new ProfileException("Cannot sign request", gse);
        }
    }

}
