package com.yoti.api.client.spi.remote.http;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Response {

    private final int code;
    private final byte[] body;
    private final Map<String, List<String>> headers;

    public Response(int code, byte[] body, Map<String, List<String>> headers) {
        this.code = code;
        this.body = clone(body);
        this.headers = headers;
    }

    public int code() {
        return code;
    }

    public byte[] body() {
        return clone(body);
    }

    private byte[] clone(byte[] body) {
        return Optional.ofNullable(body).map(byte[]::clone).orElse(null);
    }

    public Map<String, List<String>> headers() {
        return headers;
    }

}
