package com.yoti.api.client.spi.remote.http;

public class ResourceException extends Exception {

    private final int code;
    private final String body;

    public ResourceException(int responseCode, String responseMessage, String body) {
        super(responseMessage);
        this.code = responseCode;
        this.body = body;
    }

    public int code() {
        return code;
    }

    public String body() {
        return body;
    }

    @Override
    public String toString() {
        return String.format("%s, code: %s, body: %s", super.toString(), code, body);
    }

}
