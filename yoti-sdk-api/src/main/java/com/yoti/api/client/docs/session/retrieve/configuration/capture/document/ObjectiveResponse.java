package com.yoti.api.client.docs.session.retrieve.configuration.capture.document;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObjectiveResponse {

    @JsonProperty("type")
    private String type;

    /**
     * Returns the objective type as a String
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

}
