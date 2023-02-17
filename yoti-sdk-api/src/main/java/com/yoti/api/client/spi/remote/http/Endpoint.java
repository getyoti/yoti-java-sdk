package com.yoti.api.client.spi.remote.http;

import java.util.MissingFormatArgumentException;

public enum Endpoint {

    ENDPOINT("endpoint");

    final String path;
    final HttpMethod httpMethod;
    final boolean signed;

    Endpoint(HttpMethod httpMethod, String path, boolean signed) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.signed = signed;
    }

    Endpoint(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.signed = true;
    }

    Endpoint(String path, boolean signed) {
        this.httpMethod = HttpMethod.GET;
        this.path = path;
        this.signed = signed;
    }

    Endpoint(String path) {
        this.httpMethod = HttpMethod.GET;
        this.path = path;
        this.signed = true;
    }

    public HttpMethod httpMethod() {
        return httpMethod;
    }

    public String path(Object... args) {
        return formatPath(args);
    }

    private String formatPath(Object... args) {
        try {
            return String.format(path, args);
        } catch (MissingFormatArgumentException ex) {
            throw new IllegalArgumentException("Unexpected number of arguments", ex);
        }
    }

    public boolean isSigned() {
        return signed;
    }

}
