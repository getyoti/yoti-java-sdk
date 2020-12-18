package com.yoti.api.client.docs.session.create.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Filter for a required document, allowing specification
 * of restrictive parameters
 */
public abstract class DocumentFilter {

    @JsonProperty("type")
    private final String type;

    DocumentFilter(String type) {
        this.type = type;
    }

    /**
     * The type of the filter
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

}
