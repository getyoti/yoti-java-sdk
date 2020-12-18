package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationResponse {

    @JsonProperty("value")
    private String value;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("recovery_suggestion")
    private String recoverySuggestion;

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    public String getRecoverySuggestion() {
        return recoverySuggestion;
    }

}
