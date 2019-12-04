package com.yoti.api.client.spi.remote.call;

import java.util.List;
import java.util.Map;

public class SignedRequestResponse {

    private final int responseCode;
    private final byte[] responseBody;
    private final Map<String, List<String>> responseHeaders;

    public SignedRequestResponse(int responseCode, byte[] responseBody, Map<String, List<String>> responseHeaders) {
        this.responseCode = responseCode;
        this.responseBody = responseBody.clone();
        this.responseHeaders = responseHeaders;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }
}
