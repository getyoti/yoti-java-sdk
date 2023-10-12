package com.yoti.api.client;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionQrCode;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.identity.DigitalIdentityException;
import com.yoti.api.client.spi.remote.call.identity.DigitalIdentityService;
import com.yoti.api.client.spi.remote.call.identity.Receipt;
import com.yoti.validation.Validation;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DigitalIdentityClient {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private final String sdkId;
    private final KeyPair keyPair;
    private final DigitalIdentityService identityService;

    DigitalIdentityClient(String sdkId, KeyPairSource keyPair, DigitalIdentityService identityService) {
        Validation.notNullOrEmpty(sdkId, "SDK ID");
        Validation.notNull(keyPair, "Application Key Pair");

        this.sdkId = sdkId;
        this.keyPair = loadKeyPair(keyPair);
        this.identityService = identityService;
    }

    public ShareSession createShareSession(ShareSessionRequest request) throws DigitalIdentityException {
        return identityService.createShareSession(sdkId, keyPair, request);
    }

    public ShareSession fetchShareSession(String sessionId) throws DigitalIdentityException {
        return identityService.fetchShareSession(sdkId, keyPair, sessionId);
    }

    public ShareSessionQrCode createShareQrCode(String sessionId) throws DigitalIdentityException {
        return identityService.createShareQrCode(sdkId, keyPair, sessionId);
    }

    public ShareSessionQrCode fetchShareQrCode(String qrCodeId) throws DigitalIdentityException {
        return identityService.fetchShareQrCode(sdkId, keyPair, qrCodeId);
    }

    public Receipt fetchShareReceipt(String receiptId) throws DigitalIdentityException {
        return identityService.fetchShareReceipt(sdkId, keyPair, receiptId);
    }

    private KeyPair loadKeyPair(KeyPairSource keyPairSource) throws InitialisationException {
        try {
            return keyPairSource.getFromStream(new KeyStreamVisitor());
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
            Validation.notNullOrEmpty(sdkId, "SDK ID");

            this.sdkId = sdkId;
            return this;
        }

        public Builder withKeyPairSource(KeyPairSource keyPairSource) {
            Validation.notNull(keyPairSource, "Key Pair Source");

            this.keyPairSource = keyPairSource;
            return this;
        }

        public DigitalIdentityClient build() {
            return new DigitalIdentityClient(sdkId, keyPairSource, DigitalIdentityService.newInstance());
        }

    }

}
