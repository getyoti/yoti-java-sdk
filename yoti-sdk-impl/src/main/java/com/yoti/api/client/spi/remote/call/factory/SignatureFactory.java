package com.yoti.api.client.spi.remote.call.factory;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;

import static com.yoti.api.client.spi.remote.Base64.base64;

public class SignatureFactory {

    private static final String BOUNCY_CASTLE_PROVIDER = "BC";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private final MessageFactory messageFactory;

    public static SignatureFactory newInstance() {
        return new SignatureFactory(new MessageFactory());
    }

    public SignatureFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public String create(PrivateKey privateKey, String httpMethod, String path) throws GeneralSecurityException {
        byte[] message = messageFactory.create(httpMethod, path, null);
        return createSignature(message, privateKey);
    }

    public String create(PrivateKey privateKey, String httpMethod, String path, byte[] body) throws GeneralSecurityException {
        byte[] message = messageFactory.create(httpMethod, path, body);
        return createSignature(message, privateKey);
    }

    private String createSignature(byte[] message, PrivateKey privateKey) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
        signature.initSign(privateKey, new SecureRandom());
        signature.update(message);
        return base64(signature.sign());
    }

}
