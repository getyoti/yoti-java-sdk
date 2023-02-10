package com.yoti.api.client.spi.remote.call;

import java.util.List;
import java.util.Map;

public class Response {

    private final int code;
    private final byte[] body;
    private final Map<String, List<String>> headers;

    public Response(int code, byte[] body, Map<String, List<String>> headers) {
        this.code = code;
        this.body = body.clone();
        this.headers = headers;
    }

    public int code() {
        return code;
    }

    public byte[] body() {
        return body;
    }

    public Map<String, List<String>> headers() {
        return headers;
    }

}
