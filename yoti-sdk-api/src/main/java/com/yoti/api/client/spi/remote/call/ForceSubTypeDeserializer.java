package com.yoti.api.client.spi.remote.call;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

class ForceSubTypeDeserializer<T> extends JsonDeserializer<T> {

    private final Class<T> clazz;

    public ForceSubTypeDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return jsonParser.readValueAs(clazz);
    }

}
