package com.yoti.api.client.qrcode.extension;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see Extension
 *
 */
class SimpleExtension<T> implements Extension<T> {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("content")
    private final T content;

    SimpleExtension(String type, T content) {
        this.type = type;
        this.content = content;
    }

    /**
     * @see Extension#getType()
     *
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * @see Extension#getContent()
     *
     */
    @Override
    public T getContent() {
        return content;
    }

}
