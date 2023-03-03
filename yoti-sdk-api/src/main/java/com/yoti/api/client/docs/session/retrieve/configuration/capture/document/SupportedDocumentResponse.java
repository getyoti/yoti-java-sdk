package com.yoti.api.client.docs.session.retrieve.configuration.capture.document;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupportedDocumentResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("is_strictly_latin")
    private Boolean isStrictlyLatin;

    /**
     * Returns the type of document that is supported.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the flag of whether documents should be only latin or not.
     *
     * @return the flag
     */
    public Boolean getStrictlyLatin() {
        return isStrictlyLatin;
    }

}
