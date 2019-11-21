package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.HttpMethod.SUPPORTED_HTTP_METHODS;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;
import com.yoti.api.client.spi.remote.call.factory.PathFactory;
import com.yoti.api.client.spi.remote.call.factory.SignedMessageFactory;
import com.yoti.api.client.spi.remote.util.Validation;

public class SignedRequestBuilder {

    public static SignedRequestBuilder newInstance() {
        RawResourceFetcher rawResourceFetcher = new RawResourceFetcher();
        return new SignedRequestBuilder(
                new PathFactory(),
                SignedMessageFactory.newInstance(),
                new HeadersFactory(),
                JsonResourceFetcher.newInstance(rawResourceFetcher),
                rawResourceFetcher,
                ImageResourceFetcher.newInstance(rawResourceFetcher));
    }

    private KeyPair keyPair;
    private String baseUrl;
    private String endpoint;
    private byte[] payload;
    private final Map<String, String> queryParameters = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private String httpMethod;

    private final PathFactory pathFactory;
    private final SignedMessageFactory signedMessageFactory;
    private final HeadersFactory headersFactory;
    private final JsonResourceFetcher jsonResourceFetcher;
    private final RawResourceFetcher rawResourceFetcher;
    private final ImageResourceFetcher imageResourceFetcher;

    SignedRequestBuilder(PathFactory pathFactory,
            SignedMessageFactory signedMessageFactory,
            HeadersFactory headersFactory,
            JsonResourceFetcher jsonResourceFetcher,
            RawResourceFetcher rawResourceFetcher,
            ImageResourceFetcher imageResourceFetcher) {
        this.pathFactory = pathFactory;
        this.signedMessageFactory = signedMessageFactory;
        this.headersFactory = headersFactory;
        this.jsonResourceFetcher = jsonResourceFetcher;
        this.rawResourceFetcher = rawResourceFetcher;
        this.imageResourceFetcher = imageResourceFetcher;
    }

    /**
     * Proceed building the signed request with a specific key pair
     *
     * @param keyPair the key pair
     * @return the updated builder
     */
    public SignedRequestBuilder withKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
        return this;
    }

    /**
     * Proceed building the signed request with specific base url
     *
     * @param baseUrl the base url
     * @return the updated builder
     */
    public SignedRequestBuilder withBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl.replaceAll("([/]+)$", "");
        return this;
    }

    /**
     * Proceed building the signed request with a specific endpoint
     *
     * @param endpoint the endpoint
     * @return the updated builder
     */
    public SignedRequestBuilder withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Proceed building the signed request with a specific payload
     *
     * @param payload the payload
     * @return the update builder
     */
    public SignedRequestBuilder withPayload(byte[] payload) {
        this.payload = payload;
        return this;
    }

    /**
     * Proceed building the signed request with a specific query parameter
     *
     * @param name  the name of the query parameter
     * @param value the value of the query parameter
     * @return the updated builder
     */
    public SignedRequestBuilder withQueryParameter(String name, String value) {
        this.queryParameters.put(name, value);
        return this;
    }

    /**
     * Proceed building the signed request with a specific header
     *
     * @param name  the name of the header
     * @param value the value of the header
     * @return the updated builder
     */
    public SignedRequestBuilder withHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    /**
     * Proceed building the signed request with a specific HTTP method
     *
     * @param httpMethod the HTTP method
     * @return the updated builder
     */
    public SignedRequestBuilder withHttpMethod(String httpMethod) throws IllegalArgumentException {
        Validation.withinList(httpMethod, SUPPORTED_HTTP_METHODS);
        this.httpMethod = httpMethod;
        return this;
    }

    /**
     * Build the signed request with specified options
     *
     * @return the signed request
     */
    public SignedRequest build() throws GeneralSecurityException, UnsupportedEncodingException, URISyntaxException {
        validateRequest();

        if (endpoint.contains("?")) {
            endpoint = endpoint.concat("&");
        } else {
            endpoint = endpoint.concat("?");
        }

        String builtEndpoint = endpoint + createQueryParameterString(queryParameters);
        String digest = createDigest(builtEndpoint);
        headers.putAll(headersFactory.create(digest));

        return new SignedRequest(
                new URI(baseUrl + builtEndpoint),
                httpMethod,
                payload,
                headers,
                jsonResourceFetcher,
                rawResourceFetcher,
                imageResourceFetcher);
    }

    private void validateRequest() {
        Validation.notNull(keyPair, "keyPair");
        Validation.notNullOrEmpty(baseUrl, "baseUrl");
        Validation.notNullOrEmpty(endpoint, "endpoint");
        Validation.notNullOrEmpty(httpMethod, "httpMethod");
    }

    private String createQueryParameterString(Map<String, String> queryParameters) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            stringBuilder.append("&");
        }
        stringBuilder.append(pathFactory.createSignatureParams());
        return stringBuilder.toString();
    }

    private String createDigest(String endpoint) throws GeneralSecurityException {
        if (payload != null) {
            return signedMessageFactory.create(keyPair.getPrivate(), httpMethod, endpoint, payload);
        } else {
            return signedMessageFactory.create(keyPair.getPrivate(), httpMethod, endpoint);
        }
    }

}
