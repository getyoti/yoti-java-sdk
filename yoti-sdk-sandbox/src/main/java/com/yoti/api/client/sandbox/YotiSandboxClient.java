package com.yoti.api.client.sandbox;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_HOST;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.sandbox.profile.request.YotiTokenRequest;
import com.yoti.api.client.sandbox.profile.response.YotiTokenResponse;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.HttpMethod;
import com.yoti.api.client.spi.remote.call.JsonResourceFetcher;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.SignedRequest;
import com.yoti.api.client.spi.remote.call.SignedRequestBuilderFactory;
import com.yoti.api.client.spi.remote.call.YotiConstants;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YotiSandboxClient {

    private final String appId;
    private final KeyPair keyPair;
    private final String sandboxBasePath;
    private final ObjectMapper mapper;
    private final SandboxPathFactory sandboxPathFactory;
    private final JsonResourceFetcher resourceFetcher;
    private final SignedRequestBuilderFactory signedRequestBuilderFactory;

    public static YotiSandboxClientBuilder builder() {
        return new YotiSandboxClientBuilder();
    }

    YotiSandboxClient(
            String appId,
            KeyPair keyPair,
            SandboxPathFactory pathFactory,
            ObjectMapper mapper,
            JsonResourceFetcher resourceFetcher,
            SignedRequestBuilderFactory signedRequestBuilderFactory) {
        this.appId = appId;
        this.keyPair = keyPair;
        this.sandboxPathFactory = pathFactory;
        this.mapper = mapper;
        this.resourceFetcher = resourceFetcher;
        this.signedRequestBuilderFactory = signedRequestBuilderFactory;

        this.sandboxBasePath = System.getProperty(
                YotiConstants.PROPERTY_YOTI_API_URL,
                DEFAULT_YOTI_HOST + "/sandbox/v1"
        );
        notNullOrEmpty(sandboxBasePath, "Sandbox base path");
    }

    public String setupSharingProfile(YotiTokenRequest yotiTokenRequest) {
        String requestPath = sandboxPathFactory.createSandboxPath(appId);
        try {
            byte[] body = mapper.writeValueAsBytes(yotiTokenRequest);
            SignedRequest signedRequest = signedRequestBuilderFactory.create()
                    .withBaseUrl(sandboxBasePath)
                    .withEndpoint(requestPath)
                    .withKeyPair(keyPair)
                    .withHttpMethod(HttpMethod.POST)
                    .withPayload(body)
                    .build();
            YotiTokenResponse yotiTokenResponse = resourceFetcher.doRequest(signedRequest, YotiTokenResponse.class);
            return yotiTokenResponse.getToken();
        } catch (IOException | GeneralSecurityException | ResourceException | URISyntaxException e) {
            throw new SandboxException(e);
        }
    }

    public static class YotiSandboxClientBuilder {

        private static final Logger LOG = LoggerFactory.getLogger(YotiSandboxClientBuilder.class);

        private String appId;
        private KeyPair keyPair;

        private YotiSandboxClientBuilder() {
        }

        public YotiSandboxClientBuilder forApplication(String appId) {
            this.appId = appId;
            return this;
        }

        public YotiSandboxClientBuilder withKeyPair(KeyPairSource keyPair) {
            try {
                LOG.debug("Loading key pair from '{}'", keyPair);
                this.keyPair = keyPair.getFromStream(new KeyStreamVisitor());
            } catch (IOException e) {
                throw new InitialisationException("Cannot load key pair", e);
            }
            return this;
        }

        public YotiSandboxClient build() {
            notNullOrEmpty(appId, "appId");
            notNull(keyPair, "keyPair");
            return new YotiSandboxClient(appId, keyPair, new SandboxPathFactory(), new ObjectMapper(), JsonResourceFetcher.newInstance(),
                    new SignedRequestBuilderFactory());
        }

    }

}
