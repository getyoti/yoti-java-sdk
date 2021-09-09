package com.yoti.api.client.docs.session.retrieve.configuration.capture.document;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedDocumentResponse {

    @JsonProperty("type")
    private String type;

    /**
     * Returns the type of document that is supported.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

}
