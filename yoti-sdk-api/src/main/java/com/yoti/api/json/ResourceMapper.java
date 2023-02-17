package com.yoti.api.json;

import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredLivenessResourceResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ResourceMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(
                RequiredLivenessResourceResponse.class,
                new ForceSubTypeDeserializer<>(RequiredLivenessResourceResponse.class)
        );
        MAPPER.registerModule(module);
    }

    public static ObjectMapper mapper() {
        return MAPPER;
    }

}
