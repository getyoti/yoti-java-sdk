package com.yoti.auth;

import static com.yoti.validation.Validation.notNull;
import static com.yoti.validation.Validation.notNullOrEmpty;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyPair;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.spi.remote.KeyStreamVisitor;
import com.yoti.api.client.spi.remote.call.ResourceException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;

/**
 * The {@link AuthenticationTokenGenerator} is used for generation authorization tokens
 * that can be used for accessing Yoti services.  An authorization token must have
 * a unique identifier, and an expiry timestamp.  One or more scopes can be provided
 * to allow the authorization token access to different parts of Yoti systems.
 * <p>
 * The {@link AuthenticationTokenGenerator.Builder} can be accessed via {@code AuthorizationTokenGenerator.builder()}
 * method, and then configured via the fluent API.
 */
public class AuthenticationTokenGenerator {

    private final String sdkId;
    private final KeyPair keyPair;
    private final Supplier<String> jwtIdSupplier;
    private final FormRequestClient formRequestClient;

    private final URL authApiUrl;
    private final ObjectMapper objectMapper;

    AuthenticationTokenGenerator(
            String sdkId,
            KeyPair keyPair,
            Supplier<String> jwtIdSupplier,
            FormRequestClient formRequestClient,
            ObjectMapper objectMapper) {
        this.sdkId = sdkId;
        this.keyPair = keyPair;
        this.jwtIdSupplier = jwtIdSupplier;
        this.formRequestClient = formRequestClient;
        this.objectMapper = objectMapper;

        try {
            authApiUrl = new URL(System.getProperty(Properties.PROPERTY_YOTI_AUTH_URL, Properties.DEFAULT_YOTI_AUTH_URL));
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Yoti auth url", e);
        }
    }

    /**
     * Creates a new instance of {@link AuthenticationTokenGenerator.Builder}
     *
     * @return the builder
     */
    public static AuthenticationTokenGenerator.Builder builder() {
        return new AuthenticationTokenGenerator.Builder();
    }

    /**
     * Creates a new authentication token, using the supplied scopes and comment.
     *
     * @param scopes a list of scopes to be used by the authentication token
     * @return a {@link CreateAuthenticationTokenResponse} containing information about the created token.
     * @throws ResourceException if something was incorrect with the request to the Yoti authentication service
     * @throws IOException
     */
    public CreateAuthenticationTokenResponse generate(List<String> scopes) throws ResourceException, IOException {
        notNullOrEmpty(scopes, "scopes");

        String jwts = createSignedJwt(sdkId, keyPair, jwtIdSupplier, authApiUrl);

        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "client_credentials");
        formParams.put("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        formParams.put("scope", String.join(" ", scopes));
        formParams.put("client_assertion", jwts);

        byte[] responseBody = formRequestClient.performRequest(authApiUrl, "POST", formParams);

        return objectMapper.readValue(responseBody, CreateAuthenticationTokenResponse.class);
    }

    private String createSignedJwt(String sdkId, KeyPair keyPair, Supplier<String> jwtIdSupplier, URL authApiUrl) {
        String sdkIdProperty = String.format("sdk:%s", sdkId);
        OffsetDateTime now = OffsetDateTime.now();
        return Jwts.builder()
                .issuer(sdkIdProperty)
                .subject(sdkIdProperty)
                .id(jwtIdSupplier.get())
                .audience()
                .add(authApiUrl.toString())
                .and()
                .expiration(new Date(now.plusMinutes(5).toInstant().toEpochMilli()))
                .issuedAt(new Date(now.toInstant().toEpochMilli()))
                .header()
                .add("alg", "PS384")
                .add("typ", "JWT")
                .and()
                .signWith(keyPair.getPrivate(), Jwts.SIG.PS384)
                .compact();
    }

    public static final class Builder {

        private String sdkId;
        private KeyPairSource keyPairSource;
        private Supplier<String> jwtIdSupplier = () -> UUID.randomUUID().toString();

        private Builder() {
        }

        /**
         * Sets the SDK ID that the authorization token will be generated against.
         *
         * @param sdkId the SDK ID
         * @return the builder for method chaining.
         */
        public Builder withSdkId(String sdkId) {
            this.sdkId = sdkId;
            return this;
        }

        /**
         * Sets the {@link KeyPairSource} that will be used to load the {@link KeyPair}
         *
         * @param keyPairSource the key pair source that will be used to load the {@link KeyPair}
         * @return the builder for method chaining.
         */
        public Builder withKeyPairSource(KeyPairSource keyPairSource) {
            this.keyPairSource = keyPairSource;
            return this;
        }

        /**
         * Sets the supplier that will be used to generate a unique ID for the
         * authorization token.  By default, this will be a UUID v4.
         *
         * @param jwtIdSupplier the supplier used for generating authorization token ID
         * @return the builder for method chaining.
         */
        public Builder withJwtIdSupplier(Supplier<String> jwtIdSupplier) {
            this.jwtIdSupplier = jwtIdSupplier;
            return this;
        }

        /**
         * Builds an {@link AuthenticationTokenGenerator} using the values supplied to the {@link Builder}.
         *
         * @return the configured {@link AuthenticationTokenGenerator}
         */
        public AuthenticationTokenGenerator build() {
            notNullOrEmpty(sdkId, "sdkId");
            notNull(keyPairSource, "keyPairSource");
            notNull(jwtIdSupplier, "jwtIdSupplier");

            ObjectMapper objectMapper = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return new AuthenticationTokenGenerator(
                    sdkId,
                    loadKeyPair(keyPairSource),
                    jwtIdSupplier,
                    new FormRequestClient(),
                    objectMapper
            );
        }

        private KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
            try {
                return kpSource.getFromStream(new KeyStreamVisitor());
            } catch (IOException e) {
                throw new InitialisationException("Cannot load key pair", e);
            }
        }

    }

}
