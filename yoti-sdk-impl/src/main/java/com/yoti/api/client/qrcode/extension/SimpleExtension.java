package com.yoti.api.client.qrcode.extension;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @see Extension
 *
 */
class SimpleExtension implements Extension {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("content")
    private final Map<String,?> content;

    SimpleExtension(String type, Map<String,?> content) {
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
    public Map<String, ?> getContent() {
        return content;
    }

}
