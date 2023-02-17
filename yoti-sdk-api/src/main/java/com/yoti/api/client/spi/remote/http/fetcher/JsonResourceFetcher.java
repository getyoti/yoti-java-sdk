package com.yoti.api.client.spi.remote.http.fetcher;

import java.io.IOException;

import com.yoti.api.client.spi.remote.http.Request;
import com.yoti.api.client.spi.remote.http.ResourceException;
import com.yoti.api.json.ResourceMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonResourceFetcher {

    private final ObjectMapper objectMapper;
    private final RawResourceFetcher rawResourceFetcher;

    public static JsonResourceFetcher newInstance() {
        return new JsonResourceFetcher(ResourceMapper.mapper(), RawResourceFetcher.newInstance());
    }

    private JsonResourceFetcher(ObjectMapper objectMapper, RawResourceFetcher rawResourceFetcher) {
        this.objectMapper = objectMapper;
        this.rawResourceFetcher = rawResourceFetcher;
    }

    public <T> T doRequest(Request request, Class<T> clazz) throws ResourceException, IOException {
        return objectMapper.readValue(rawResourceFetcher.doRequest(request).body(), clazz);
    }

}
