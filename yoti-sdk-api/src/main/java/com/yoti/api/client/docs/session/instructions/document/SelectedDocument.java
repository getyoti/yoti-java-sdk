package com.yoti.api.client.docs.session.instructions.document;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SelectedDocument {

    @JsonProperty("type")
    private final String type;

    SelectedDocument(String type) {
        this.type = type;
    }

    /**
     * The type of document that will be used to satisfy the document requirement
     *
     * @return the document type
     */
    public String getType() {
        return type;
    }

}
