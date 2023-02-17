package com.yoti.api.client.spi.remote.http;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

import com.yoti.api.client.Image;
import com.yoti.api.client.spi.remote.http.fetcher.ImageResourceFetcher;
import com.yoti.api.client.spi.remote.http.fetcher.JsonResourceFetcher;
import com.yoti.api.client.spi.remote.http.fetcher.RawResourceFetcher;

public class Request {

    private static final RawResourceFetcher rawResourceFetcher = RawResourceFetcher.newInstance();
    private static final JsonResourceFetcher jsonResourceFetcher = JsonResourceFetcher.newInstance();
    private static final ImageResourceFetcher imageResourceFetcher = ImageResourceFetcher.newInstance();

    private final URI uri;
    private final HttpMethod method;
    private final byte[] data;
    private final Map<String, String> headers;

    public Request(Builder builder) {
        this.uri = builder.uri;
        this.method = builder.method;
        this.data = builder.data;
        this.headers = builder.headers;
    }

    public URI uri() {
        return uri;
    }

    public HttpMethod method() {
        return method;
    }

    public byte[] data() {
        return Optional.ofNullable(data).map(byte[]::clone).orElse(null);
    }

    public Map<String, String> headers() {
        return headers;
    }

    public static Builder forUri(URI uri) {
        return new Builder(uri);
    }

    public <T> T execute(Class<T> clazz) throws ResourceException, IOException {
        if (Image.class.isAssignableFrom(clazz)) {
            return clazz.cast(imageResourceFetcher.doRequest(this));
        }
        return jsonResourceFetcher.doRequest(this, clazz);
    }

    public Response execute() throws ResourceException, IOException {
        return rawResourceFetcher.doRequest(this);
    }

    public static final class Builder {

        private final URI uri;

        private HttpMethod method;
        private Map<String, String> headers;
        private byte[] data;

        private Builder(URI uri) {
            this.uri = uri;
        }

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder data(byte[] data) {
            this.data = data.clone();
            return this;
        }

        public Request build() {
            return new Request(this);
        }

    }

}
