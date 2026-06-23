package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes the error that occurred when a digital ID share was not completed successfully.
 */
public class DigitalIdShareErrorResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("description")
    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
