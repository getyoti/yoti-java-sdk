package com.yoti.api.client.spi.remote.call;

import java.io.IOException;

import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredLivenessResourceResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public final class JsonResourceFetcher implements ResourceFetcher {

    private final ObjectMapper objectMapper;
    private final RawResourceFetcher rawResourceFetcher;

    public static JsonResourceFetcher newInstance() {
        return JsonResourceFetcher.newInstance(new RawResourceFetcher());
    }

    static JsonResourceFetcher newInstance(RawResourceFetcher rawResourceFetcher) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(
                RequiredLivenessResourceResponse.class,
                new ForceSubTypeDeserializer<>(RequiredLivenessResourceResponse.class)
        );
        objectMapper.registerModule(simpleModule);

        return new JsonResourceFetcher(objectMapper, rawResourceFetcher);
    }

    private JsonResourceFetcher(ObjectMapper objectMapper,
            RawResourceFetcher rawResourceFetcher) {
        this.objectMapper = objectMapper;
        this.rawResourceFetcher = rawResourceFetcher;
    }

    @Override
    public <T> T doRequest(SignedRequest signedRequest, Class<T> resourceClass) throws ResourceException, IOException {
        SignedRequestResponse signedRequestResponse = rawResourceFetcher.doRequest(signedRequest);
        return objectMapper.readValue(signedRequestResponse.getResponseBody(), resourceClass);
    }

}
