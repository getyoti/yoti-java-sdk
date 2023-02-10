package com.yoti.api.client.spi.remote.call;

public class ResourceException extends Exception {

    private final int code;
    private final String body;

    public ResourceException(int code, String message, String body) {
        super(message);
        this.code = code;
        this.body = body;
    }

    public ResourceException(int code, String message) {
        super(message);
        this.code = code;
        body = null;
    }

    public String body() {
        return body;
    }

    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s, code: %s, body: %s", super.toString(), code, body);
    }

}
