package com.yoti.api.client.spi.remote.call.factory;

import static com.yoti.api.client.spi.remote.Base64.base64;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SIGNATURE_ALGORITHM;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SignedMessageFactory {

    private final MessageFactory messageFactory;

    public static SignedMessageFactory newInstance() {
        return new SignedMessageFactory(new MessageFactory());
    }

    public SignedMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public String create(PrivateKey privateKey, String httpMethod, String path) throws GeneralSecurityException {
        byte[] message = messageFactory.create(httpMethod, path, null);
        return signMessage(message, privateKey);
    }

    public String create(PrivateKey privateKey, String httpMethod, String path, byte[] body) throws GeneralSecurityException {
        byte[] message = messageFactory.create(httpMethod, path, body);
        return signMessage(message, privateKey);
    }

    private String signMessage(byte[] message, PrivateKey privateKey) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
        signature.initSign(privateKey, new SecureRandom());
        signature.update(message);
        return base64(signature.sign());
    }

}
