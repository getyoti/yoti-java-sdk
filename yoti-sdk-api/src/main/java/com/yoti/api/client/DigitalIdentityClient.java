package com.yoti.api.client;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.identity.MatchRequest;
import com.yoti.api.client.identity.MatchResult;
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

    private final KeyPair keyPair;
    private final DigitalIdentityService identityService;

    private DigitalIdentityClient(KeyPair keyPair, DigitalIdentityService identityService) {
        this.keyPair = keyPair;
        this.identityService = identityService;
    }

    public ShareSession createShareSession(ShareSessionRequest request) throws DigitalIdentityException {
        return identityService.createShareSession(request);
    }

    public ShareSession fetchShareSession(String sessionId) throws DigitalIdentityException {
        return identityService.fetchShareSession(sessionId);
    }

    public ShareSessionQrCode createShareQrCode(String sessionId) throws DigitalIdentityException {
        return identityService.createShareQrCode(sessionId);
    }

    public ShareSessionQrCode fetchShareQrCode(String qrCodeId) throws DigitalIdentityException {
        return identityService.fetchShareQrCode(qrCodeId);
    }

    public Receipt fetchShareReceipt(String receiptId) throws DigitalIdentityException {
        return identityService.fetchShareReceipt(keyPair, receiptId);
    }

    public MatchResult fetchMatch(MatchRequest request) throws DigitalIdentityException {
        return identityService.fetchMatch(request);
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
            Validation.notNullOrEmpty(sdkId, "SDK ID");
            Validation.notNull(keyPairSource, "Application Key Pair");

            KeyPair keyPair = loadKeyPair(keyPairSource);
            return new DigitalIdentityClient(keyPair, DigitalIdentityService.newInstance(keyPair, sdkId));
        }

        private KeyPair loadKeyPair(KeyPairSource keyPairSource) throws InitialisationException {
            try {
                return keyPairSource.getFromStream(new KeyStreamVisitor());
            } catch (IOException ex) {
                throw new InitialisationException("Cannot load Key Pair", ex);
            }
        }

    }

}
