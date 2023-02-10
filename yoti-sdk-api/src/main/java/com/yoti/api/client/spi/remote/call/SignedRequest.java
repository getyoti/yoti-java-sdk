package com.yoti.api.client.spi.remote.call;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import com.yoti.api.client.Image;

public class SignedRequest {

    private final URI uri;
    private final String method;
    private final byte[] data;
    private final Map<String, String> headers;
    private final JsonResourceFetcher jsonResourceFetcher;
    private final RawResourceFetcher rawResourceFetcher;
    private final ImageResourceFetcher imageResourceFetcher;

    SignedRequest(final URI uri,
            final String method,
            final byte[] data,
            final Map<String, String> headers,
            JsonResourceFetcher jsonResourceFetcher,
            RawResourceFetcher rawResourceFetcher,
            ImageResourceFetcher imageResourceFetcher) {

        this.uri = uri;
        this.method = method;
        this.data = data;
        this.headers = headers;
        this.jsonResourceFetcher = jsonResourceFetcher;
        this.rawResourceFetcher = rawResourceFetcher;
        this.imageResourceFetcher = imageResourceFetcher;
    }

    public URI getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public byte[] getData() {
        return data != null ? data.clone() : null;
    }

    public Map<String, String> getHeaders() {
        return headers;
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

}
