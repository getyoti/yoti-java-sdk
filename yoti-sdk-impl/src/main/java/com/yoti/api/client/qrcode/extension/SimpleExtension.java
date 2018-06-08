package com.yoti.api.client.qrcode.extension;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see Extension
 *
 */
public class SimpleExtension implements Extension {

    @JsonProperty("type")
    private final Extension.Type type;

    @JsonProperty("content")
    private final String content;

    public SimpleExtension(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    /**
     * @see Extension#getType()
     *
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * @see Extension#getContent()
     *
     */
    @Override
    public String getContent() {
        return content;
    }

}
