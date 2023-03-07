package com.yoti.api.client;

import static com.yoti.validation.Validation.notNull;
import static com.yoti.validation.Validation.notNullOrEmpty;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.identity.DigitalIdentityService;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DigitalIdentityClient {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final String sdkId;
    private final KeyPair keyPair;
    private final DigitalIdentityService identityService;

    DigitalIdentityClient(String sdkId, KeyPairSource keyPair, DigitalIdentityService identityService) {
        this.sdkId = sdkId;
        this.keyPair = loadKeyPair(keyPair);
        this.identityService = identityService;
    }

    public Object createShareSession() {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");

        return identityService.createShareSession();
    }

    public Object fetchShareSession(String sessionId) {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");

        return identityService.fetchShareSession(sessionId);
    }

    public Object createShareQrCode(String sessionId) {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");

        return identityService.createShareQrCode(sessionId);
    }

    public Object fetchShareQrCode(String qrCodeId) {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");

        return identityService.fetchShareQrCode(qrCodeId);
    }

    public Object fetchShareReceipt(String receiptId) {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");

        return identityService.fetchShareReceipt(receiptId);
    }

    private KeyPair loadKeyPair(KeyPairSource keyPair) throws InitialisationException {
        try {
            return keyPair.getFromStream(new KeyStreamVisitor());
        } catch (IOException ex) {
            throw new InitialisationException("Cannot load Key Pair", ex);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String sdkId;
        private KeyPairSource keyPairSource;

        private Builder() { }

        public Builder withClientSdkId(String sdkId) {
            notNullOrEmpty(sdkId, "SDK ID");

            this.sdkId = sdkId;
            return this;
        }

        public Builder withKeyPairSource(KeyPairSource keyPairSource) {
            notNull(keyPairSource, "Key Pair Source");

            this.keyPairSource = keyPairSource;
            return this;
        }

        public DigitalIdentityClient build() {
            notNull(sdkId, "SDK ID");
            notNull(keyPairSource, "Key Pair Source");

            return new DigitalIdentityClient(sdkId, keyPairSource, DigitalIdentityService.newInstance());
        }

    }

}