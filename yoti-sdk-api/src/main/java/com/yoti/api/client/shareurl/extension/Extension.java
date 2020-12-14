package com.yoti.api.client.shareurl.extension;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Type and content of a feature for an application
 */
public class Extension<T> {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("content")
    private final T content;

    Extension(String type, T content) {
        this.type = type;
        this.content = content;
    }

    /**
     * Get the feature's type
     *
     * @return the type of the operation
     */
    public String getType() {
        return type;
    }

    /**
     * Get the feature's details
     *
     * @return the payload of the operation
     */
    public T getContent() {
        return content;
    }

}
