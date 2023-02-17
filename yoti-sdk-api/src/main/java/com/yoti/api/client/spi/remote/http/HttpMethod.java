package com.yoti.api.client.spi.remote.http;

public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    final String name;

    HttpMethod(String name) {
        this.name = name;
    }

}
