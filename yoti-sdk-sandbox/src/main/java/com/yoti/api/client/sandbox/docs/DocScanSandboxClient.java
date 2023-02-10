package com.yoti.api.client.sandbox.docs;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_HOST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.PROPERTY_YOTI_DOCS_URL;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.sandbox.SandboxException;
import com.yoti.api.client.sandbox.docs.request.ResponseConfig;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.HttpMethod;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocScanSandboxClient {

    private static final String DOC_SCAN_SANDBOX_PATH_PREFIX = "/sandbox/idverify/v1";
    private static final String DEFAULT_DOC_SCAN_SANDBOX_API_URL = DEFAULT_YOTI_HOST + DOC_SCAN_SANDBOX_PATH_PREFIX;

    private final String docScanBaseUrl;
    private final ObjectMapper mapper;
    private final SignedRequestBuilderFactory signedRequestBuilderFactory;
    private final String sdkId;
    private final KeyPair keyPair;

    DocScanSandboxClient(String sdkId,
            KeyPair keyPair,
            ObjectMapper mapper,
            SignedRequestBuilderFactory signedRequestBuilderFactory) {
        this.sdkId = sdkId;
        this.keyPair = keyPair;
        this.mapper = mapper;
        this.signedRequestBuilderFactory = signedRequestBuilderFactory;
        this.docScanBaseUrl = System.getProperty(PROPERTY_YOTI_DOCS_URL, DEFAULT_DOC_SCAN_SANDBOX_API_URL);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Configures the response for the given session ID.
     *
     * @param sessionId      the session ID
     * @param responseConfig the response configuration
     * @throws SandboxException - if there was a problem configuring the response
     */
    public void configureSessionResponse(String sessionId, ResponseConfig responseConfig) throws SandboxException {
        String path = String.format("/sessions/%s/response-config", sessionId);

        try {
            byte[] body = mapper.writeValueAsBytes(responseConfig);

            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withBaseUrl(docScanBaseUrl)
                    .withEndpoint(path)
                    .withKeyPair(keyPair)
                    .withHttpMethod(HttpMethod.PUT)
                    .withPayload(body)
                    .withQueryParameter("sdkId", sdkId)
                    .build();

            signedRequest.execute();
        } catch (URISyntaxException | GeneralSecurityException | ResourceException | IOException e) {
            throw new SandboxException(e);
        }
    }

    /**
     * Configures the default response for the application
     *
     */
    public void configureApplicationResponse(ResponseConfig sandboxExpectation) throws SandboxException {
        String path = String.format("/apps/%s/response-config", sdkId);

        try {
            byte[] body = mapper.writeValueAsBytes(sandboxExpectation);

            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withBaseUrl(docScanBaseUrl)
                    .withEndpoint(path)
                    .withKeyPair(keyPair)
                    .withHttpMethod(HttpMethod.PUT)
                    .withPayload(body)
                    .build();

            signedRequest.execute();
        } catch (URISyntaxException | GeneralSecurityException | ResourceException | IOException e) {
            throw new SandboxException(e);
        }
    }

    public static class Builder {

        private static final Logger LOGGER = LoggerFactory.getLogger(Builder.class);

        private String sdkId;
        private KeyPair keyPair;

        private Builder() {
        }

        public Builder withSdkId(String sdkId) {
            this.sdkId = sdkId;
            return this;
        }

        public Builder withKeyPair(KeyPairSource keyPairSource) {
            try {
                LOGGER.debug("Loading key pair from '{}'", keyPairSource);
                this.keyPair = keyPairSource.getFromStream(new KeyStreamVisitor());
            } catch (IOException e) {
                throw new InitialisationException("Cannot load key pair", e);
            }
            return this;
        }

        public DocScanSandboxClient build() {
            notNullOrEmpty(sdkId, "sdkId");
            notNull(keyPair, "keyPair");

            return new DocScanSandboxClient(sdkId, keyPair, new ObjectMapper(), new SignedRequestBuilderFactory());
        }
    }
}
