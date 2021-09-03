package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import java.io.IOException;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class AbstractClassWithTypeInfoDeserializer<T> extends JsonDeserializer<T> {

    private final Class<T> clazz;

    public AbstractClassWithTypeInfoDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return jsonParser.readValueAs(clazz);
    }

}
