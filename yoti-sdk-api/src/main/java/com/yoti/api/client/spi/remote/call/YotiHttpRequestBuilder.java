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

import com.yoti.api.client.spi.remote.call.factory.AmlSignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.AuthStrategy;
import com.yoti.api.client.spi.remote.call.factory.AuthTokenStrategy;
import com.yoti.api.client.spi.remote.call.factory.DocsSignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.HeadersFactory;
import com.yoti.api.client.spi.remote.call.factory.ProfileSignedRequestStrategy;
import com.yoti.api.client.spi.remote.call.factory.SimpleSignedRequestStrategy;
import com.yoti.validation.Validation;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class YotiHttpRequestBuilder {

    private AuthStrategy authStrategy;
    private String baseUrl;
    private String endpoint;
    private byte[] payload;
    private final Map<String, String> queryParameters = new HashMap<>();
    private final Map<String, String> headers = new HashMap<>();
    private String httpMethod;

    private final HeadersFactory headersFactory;
    private final JsonResourceFetcher jsonResourceFetcher;
    private final RawResourceFetcher rawResourceFetcher;
    private final ImageResourceFetcher imageResourceFetcher;
    private MultipartEntityBuilder multipartEntityBuilder;

    YotiHttpRequestBuilder(HeadersFactory headersFactory,
            JsonResourceFetcher jsonResourceFetcher,
            RawResourceFetcher rawResourceFetcher,
            ImageResourceFetcher imageResourceFetcher) {
        this.headersFactory = headersFactory;
        this.jsonResourceFetcher = jsonResourceFetcher;
        this.rawResourceFetcher = rawResourceFetcher;
        this.imageResourceFetcher = imageResourceFetcher;
    }

    /**
     * Proceed building the signed request with a specific key pair
     *
     * @param authStrategy the key pair
     * @return the updated builder
     */
    public YotiHttpRequestBuilder withAuthStrategy(AuthStrategy authStrategy) {
        this.authStrategy = authStrategy;
        return this;
    }

    // FIXME: Test me?
    public YotiHttpRequestBuilder forAuthTokenRequest(String authToken) {
        this.authStrategy = new AuthTokenStrategy(authToken);
        return this;
    }

    // FIXME: Test me?
    public YotiHttpRequestBuilder forDocsSignedRequest(KeyPair keyPair, String sdkId) {
        this.authStrategy = new DocsSignedRequestStrategy(keyPair, sdkId);
        return this;
    }

    // FIXME: Test me?
    public YotiHttpRequestBuilder forAmlSignedRequest(KeyPair keyPair, String appId) {
        this.authStrategy = new AmlSignedRequestStrategy(keyPair, appId);
        return this;
    }

    // FIXME: Test me?
    public YotiHttpRequestBuilder forProfileRequest(KeyPair keyPair, String appId) {
        this.authStrategy = new ProfileSignedRequestStrategy(keyPair, appId);
        return this;
    }

    // FIXME: Test me?
    public YotiHttpRequestBuilder forSignedRequest(KeyPair keyPair) {
        this.authStrategy = new SimpleSignedRequestStrategy(keyPair);
        return this;
    }

    /**
     * Proceed building the signed request with specific base url
     *
     * @param baseUrl the base url
     * @return the updated builder
     */
    public YotiHttpRequestBuilder withBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl.replaceAll("([/]+)$", "");
        return this;
    }

    /**
     * Proceed building the signed request with a specific endpoint
     *
     * @param endpoint the endpoint
     * @return the updated builder
     */
    public YotiHttpRequestBuilder withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Proceed building the signed request with a specific payload
     *
     * @param payload the payload
     * @return the update builder
     */
    public YotiHttpRequestBuilder withPayload(byte[] payload) {
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
    public YotiHttpRequestBuilder withQueryParameter(String name, String value) {
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
    public YotiHttpRequestBuilder withHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    /**
     * Proceed building the signed request with a specific HTTP method
     *
     * @param httpMethod the HTTP method
     * @return the updated builder
     */
    public YotiHttpRequestBuilder withHttpMethod(String httpMethod) throws IllegalArgumentException {
        Validation.withinList(httpMethod, SUPPORTED_HTTP_METHODS);
        this.httpMethod = httpMethod;
        return this;
    }

    private YotiHttpRequestBuilder forMultipartRequest() {
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
    public YotiHttpRequestBuilder withMultipartBoundary(String multipartBoundary) {
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
    public YotiHttpRequestBuilder withMultipartBinaryBody(String name, byte[] payload, ContentType contentType, String fileName) {
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
    public YotiHttpRequest build() throws GeneralSecurityException, UnsupportedEncodingException, URISyntaxException {
        validateRequest();
        String builtEndpoint = endpoint + createQueryParameterString();

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

        Header authHeader = authStrategy.createAuthHeader(httpMethod, builtEndpoint, finalPayload);
        headers.putAll(headersFactory.create(authHeader));

        return new YotiHttpRequest(
                new URI(baseUrl + builtEndpoint),
                httpMethod,
                finalPayload,
                headers,
                jsonResourceFetcher,
                rawResourceFetcher,
                imageResourceFetcher);
    }

    private void validateRequest() {
        Validation.notNull(authStrategy, "authStrategy");
        Validation.notNullOrEmpty(baseUrl, "baseUrl");
        Validation.notNullOrEmpty(endpoint, "endpoint");
        Validation.notNullOrEmpty(httpMethod, "httpMethod");
    }

    private String createQueryParameterString() throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
        }

        for (NameValuePair queryParam : authStrategy.createQueryParams()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(queryParam);
        }

        String built = stringBuilder.toString();
        if (built.isEmpty()) {
            return built;
        }

        String prefix = endpoint.contains("?") ? "&" : "?";
        return prefix.concat(built);
    }

}
