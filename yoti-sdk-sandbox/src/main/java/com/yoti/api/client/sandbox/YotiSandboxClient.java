package com.yoti.api.client.sandbox;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_YOTI_HOST;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.sandbox.profile.request.YotiTokenRequest;
import com.yoti.api.client.sandbox.profile.response.YotiTokenResponse;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.HttpMethod;
import com.yoti.api.client.spi.remote.call.JsonResourceFetcher;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.client.spi.remote.call.ResourceFetcher;
import com.yoti.api.client.spi.remote.call.UrlConnector;
import com.yoti.api.client.spi.remote.call.YotiConstants;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YotiSandboxClient {

    public static final String YOTI_SANDBOX_PATH_PREFIX = "/sandbox/v1";
    private static final String DEFAULT_SANDBOX_API_URL = DEFAULT_YOTI_HOST + YOTI_SANDBOX_PATH_PREFIX;

    private final String appId;
    private final KeyPair keyPair;
    private final String sandboxBasePath;
    private final ObjectMapper mapper;
    private final SandboxPathFactory sandboxPathFactory;
    private final SignedMessageFactory signedMessageFactory;
    private final ResourceFetcher resourceFetcher;

    public static YotiSandboxClientBuilder builder() {
        return new YotiSandboxClientBuilder();
    }

    YotiSandboxClient(String appId,
            KeyPair keyPair,
            SandboxPathFactory pathFactory,
            ObjectMapper mapper,
            SignedMessageFactory signedMessageFactory,
            ResourceFetcher resourceFetcher) {
        this.appId = appId;
        this.keyPair = keyPair;
        this.sandboxPathFactory = pathFactory;
        this.mapper = mapper;
        this.signedMessageFactory = signedMessageFactory;
        this.resourceFetcher = resourceFetcher;

        this.sandboxBasePath = System.getProperty(YotiConstants.PROPERTY_YOTI_API_URL, DEFAULT_SANDBOX_API_URL);
        notNullOrEmpty(sandboxBasePath, "Sandbox base path");
    }

    public String setupSharingProfile(YotiTokenRequest yotiTokenRequest) {
        String requestPath = sandboxPathFactory.createSandboxPath(appId);

        try {
            byte[] body = mapper.writeValueAsBytes(yotiTokenRequest);
            String requestDigest = signedMessageFactory.create(keyPair.getPrivate(), HttpMethod.HTTP_POST, requestPath, body);

            Map<String, String> headers = new HashMap<>();
            headers.put(YotiConstants.DIGEST_HEADER, requestDigest);
            headers.put(YOTI_SDK_HEADER, JAVA);
            headers.put(CONTENT_TYPE, CONTENT_TYPE_JSON);

            UrlConnector urlConnector = UrlConnector.get(sandboxBasePath + requestPath);

            YotiTokenResponse yotiTokenResponse = resourceFetcher.postResource(urlConnector, body, headers, YotiTokenResponse.class);
            return yotiTokenResponse.getToken();
        } catch (IOException | GeneralSecurityException | ResourceException e) {
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
            return new YotiSandboxClient(appId, keyPair, new SandboxPathFactory(), new ObjectMapper(), SignedMessageFactory.newInstance(),
                    JsonResourceFetcher.newInstance());
        }

    }

}
