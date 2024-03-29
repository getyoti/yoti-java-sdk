package com.yoti.api.client.identity.extension;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Extension<T> {

    @JsonProperty(Property.TYPE)
    private final String type;

    @JsonProperty(Property.CONTENT)
    private final T content;

    @JsonCreator
    Extension(@JsonProperty(Property.TYPE) String type, @JsonProperty(Property.CONTENT) T content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public T getContent() {
        return content;
    }

    private static final class Property {

        private static final String TYPE = "type";
        private static final String CONTENT = "content";

    }

}
