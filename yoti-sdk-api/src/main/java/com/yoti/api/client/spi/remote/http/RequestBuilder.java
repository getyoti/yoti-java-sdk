package com.yoti.api.client.spi.remote.http;

import static java.lang.System.nanoTime;
import static java.util.UUID.randomUUID;

import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE;
import static com.yoti.api.client.spi.remote.call.YotiConstants.CONTENT_TYPE_JSON;
import static com.yoti.api.client.spi.remote.call.YotiConstants.DIGEST_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA;
import static com.yoti.api.client.spi.remote.call.YotiConstants.JAVA_VERSION_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SDK_VERSION;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_HEADER;
import static com.yoti.api.client.spi.remote.call.YotiConstants.YOTI_SDK_VERSION_HEADER;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.yoti.api.client.spi.remote.util.Validation;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class RequestBuilder {

    public static Builder forEndpoint(Endpoint endpoint, String... params) {
        Validation.notNull(endpoint, "endpoint");

        return new Builder(endpoint, params);
    }

    public static final class Builder {

        private final Endpoint endpoint;
        private final Object[] endpointParams;
        private final Map<String, String> headers;
        private final Map<String, String> queryParameters;

        private String baseUrl;
        private byte[] payload;
        private KeyPair keyPair;

        private MultipartEntityBuilder multipartEntityBuilder;

        private Builder(Endpoint endpoint, String... params) {
            this.endpoint = endpoint;
            this.endpointParams = params;

            this.queryParameters = new HashMap<>();
            this.headers = new HashMap<>();
        }

        public Builder withBaseUrl(String baseUrl) {
            Validation.notNull(baseUrl, "baseUrl");

            this.baseUrl = baseUrl.replaceAll("(/+)$", "");
            return this;
        }

        public Builder withQueryParameters(Map<String, String> queryParameters) {
            this.queryParameters.putAll(queryParameters);
            return this;
        }

        public Builder withQueryParameter(String name, String value) {
            queryParameters.put(name, value);
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder withHeader(String name, String value) {
            headers.put(name, value);
            return this;
        }

        public Builder withPayload(byte[] payload) {
            this.multipartEntityBuilder = null;
            this.payload = payload.clone();
            headers.put(CONTENT_TYPE, CONTENT_TYPE_JSON);
            return this;
        }

        public Builder withKeyPair(KeyPair keyPair) {
            Validation.notNull(keyPair, "keyPair");
            this.keyPair = keyPair;
            return this;
        }

        public Builder withMultipartBoundary(String boundary) {
            Validation.notNullOrEmpty(boundary, "multipart boundary");

            multipartEntityBuilder = Optional.ofNullable(multipartEntityBuilder)
                    .orElseGet(MultipartEntityBuilder::create);

            HttpEntity httpEntity = multipartEntityBuilder.setBoundary(boundary).build();

            this.payload = writeMultipartPayload(httpEntity);
            return this;
        }

        public Builder withMultipartBinaryBody(String name, byte[] payload, ContentType contentType, String fileName) {
            Validation.notNullOrEmpty(name, "name");
            Validation.notNull(payload, "payload");
            Validation.notNull(contentType, "contentType");
            Validation.notNullOrEmpty(fileName, "fileName");

            multipartEntityBuilder = Optional.ofNullable(multipartEntityBuilder)
                    .orElseGet(MultipartEntityBuilder::create);

            HttpEntity httpEntity = multipartEntityBuilder.addBinaryBody(name, payload, contentType, fileName).build();

            this.payload = writeMultipartPayload(httpEntity);
            headers.put(CONTENT_TYPE, httpEntity.getContentType().getElements()[0].toString());
            return this;
        }

        private static byte[] writeMultipartPayload(HttpEntity httpEntity) {
            ByteArrayOutputStream multipartStream = new ByteArrayOutputStream();
            try {
                httpEntity.writeTo(multipartStream);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }

            return multipartStream.toByteArray();
        }

        public Request build() throws URISyntaxException, GeneralSecurityException {
            Validation.notNullOrEmpty(baseUrl, "baseUrl");

            String path = endpoint.path(endpointParams);

            if (!queryParameters.isEmpty()) {
                path += (path.contains("?") ? "&" : "?") + formatQueryParams(queryParameters);
            }

            if (endpoint.isSigned()) {
                path += (path.contains("?") ? "&" : "?") + formatSignatureParams();
            }

            URI requestUri = new URI(baseUrl + path);

            if (endpoint.isSigned()) {
                Validation.notNull(keyPair, "keyPair");

                Crypto.Builder digestBuilder = Crypto.forPrivateKey(keyPair.getPrivate())
                        .httpMethod(endpoint.httpMethod())
                        .path(path);

                Optional.ofNullable(payload).map(digestBuilder::body);

                String sign = digestBuilder.sign();
                headers.put(DIGEST_HEADER, sign);
            }

            headers.put(YOTI_SDK_HEADER, JAVA);
            headers.put(YOTI_SDK_VERSION_HEADER, SDK_VERSION);
            headers.put(JAVA_VERSION_HEADER, System.getProperty("java.version"));

            Request.Builder build = Request.forUri(requestUri)
                    .headers(headers)
                    .method(endpoint.httpMethod());

            Optional.ofNullable(payload).map(build::data);

            return build.build();
        }

        private static String formatQueryParams(Map<String, String> queryParams) {
            return queryParams.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));
        }

        private static String formatSignatureParams() {
            return String.format("nonce=%s&timestamp=%s", randomUUID(), nanoTime() / 1000);
        }

    }

}
