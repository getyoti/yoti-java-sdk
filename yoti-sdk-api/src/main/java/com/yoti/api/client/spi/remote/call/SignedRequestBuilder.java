package com.yoti.api.client.spi.remote.call;

import static com.yoti.api.client.spi.remote.call.HttpMethod.SUPPORTED_HTTP_METHODS;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class SignedRequestBuilder {

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
    private MultipartEntityBuilder multipartEntityBuilder;

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
        this.multipartEntityBuilder = null;
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

    private SignedRequestBuilder forMultipartRequest() {
        if (this.multipartEntityBuilder == null) {
            this.multipartEntityBuilder = MultipartEntityBuilder.create();
        }
        this.payload = null;
        return this;
    }

    /**
     * Sets the boundary to be used on the multipart request
     *
     * @param multipartBoundary the multipart boundary
     * @return the updated builder
     */
    public SignedRequestBuilder withMultipartBoundary(String multipartBoundary) {
        Validation.notNullOrEmpty(multipartBoundary, "multipartBoundary");
        forMultipartRequest();
        multipartEntityBuilder.setBoundary(multipartBoundary);
        return this;
    }

    /**
     * Adds a binary body to the multipart request.
     * <p>
     * Note: the Signed Request must be specified with a boundary also
     * in order to make use of the Multipart request
     *
     * @param name        the name of the binary body
     * @param payload     the payload of the binary body
     * @param contentType the content type of the binary body
     * @param fileName    the filename of the binary body
     * @return the updated builder
     */
    public SignedRequestBuilder withMultipartBinaryBody(String name, byte[] payload, ContentType contentType, String fileName) {
        Validation.notNullOrEmpty(name, "name");
        Validation.notNull(payload, "payload");
        Validation.notNull(contentType, "contentType");
        Validation.notNullOrEmpty(fileName, "fileName");
        forMultipartRequest();
        multipartEntityBuilder.addBinaryBody(name, payload, contentType, fileName);
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

        byte[] finalPayload;
        if (multipartEntityBuilder == null) {
            headers.put(CONTENT_TYPE, CONTENT_TYPE_JSON);
            finalPayload = payload;
        } else {
            try {
                HttpEntity httpEntity = multipartEntityBuilder.build();
                headers.put(CONTENT_TYPE, httpEntity.getContentType().getElements()[0].toString());
                ByteArrayOutputStream multipartStream = new ByteArrayOutputStream();
                httpEntity.writeTo(multipartStream);
                finalPayload = multipartStream.toByteArray();
            } catch (IOException ioe) {
                throw new IllegalStateException(ioe);
            }
        }

        String digest = createDigest(builtEndpoint, finalPayload);
        headers.putAll(headersFactory.create(digest));

        return new SignedRequest(
                new URI(baseUrl + builtEndpoint),
                httpMethod,
                finalPayload,
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

    private String createDigest(String endpoint, byte[] payload) throws GeneralSecurityException {
        if (payload != null) {
            return signedMessageFactory.create(keyPair.getPrivate(), httpMethod, endpoint, payload);
        } else {
            return signedMessageFactory.create(keyPair.getPrivate(), httpMethod, endpoint);
        }
    }

}
