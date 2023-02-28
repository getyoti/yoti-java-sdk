package com.yoti.api.client;

import static com.yoti.validation.Validation.notNull;
import static com.yoti.validation.Validation.notNullOrEmpty;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Security;

import com.yoti.api.client.identity.ShareSession;
import com.yoti.api.client.identity.ShareSessionQrCode;
import com.yoti.api.client.identity.ShareSessionRequest;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.identity.DigitalIdentityException;
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
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPair, "Application Key Pair");

        this.sdkId = sdkId;
        this.keyPair = loadKeyPair(keyPair);
        this.identityService = identityService;
    }

    /**
     * Create a sharing session to initiate a sharing process based on a policy
     *
     * @param request Details of the request like policy, extensions and push notification for the application
     * @return an {@link ShareSession} ID, status and expiry of the newly created Share Session
     * @throws DigitalIdentityException Thrown if the session creation is unsuccessful
     */
    public ShareSession createShareSession(ShareSessionRequest request) throws DigitalIdentityException {
        return identityService.createShareSession(sdkId, keyPair, request);
    }

    public Object fetchShareSession(String sessionId) {
        return identityService.fetchShareSession(sessionId);
    }

    /**
     * Create a sharing session QR code to initiate a sharing process based on a policy
     *
     * @param sessionId Session ID the QR code will belong to
     * @return ID and URI of the newly created Share Session QR code
     * @throws DigitalIdentityException Thrown if the QR code creation is unsuccessful
     */
    public ShareSessionQrCode createShareQrCode(String sessionId) throws DigitalIdentityException {
        return identityService.createShareQrCode(sdkId, keyPair, sessionId);
    }

    public Object fetchShareQrCode(String qrCodeId) {
        return identityService.fetchShareQrCode(qrCodeId);
    }

    public Object fetchShareReceipt(String receiptId) {
        return identityService.fetchShareReceipt(receiptId);
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
            return new DigitalIdentityClient(sdkId, keyPairSource, DigitalIdentityService.newInstance());
        }

    }

}
