package com.yoti.api.client.qrcode.extension;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SimpleExtension
 */
public class SimpleExtension implements Extension {

    /**
     * type
     */
    @JsonProperty("type")
    private final Extension.Type type;

    /**
     * content
     */
    @JsonProperty("content")
    private final String content; //JsonObject

    public SimpleExtension(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getContent() {
        return content;
    }

}
