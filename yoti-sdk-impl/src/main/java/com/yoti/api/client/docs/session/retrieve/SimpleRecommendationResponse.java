package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleRecommendationResponse implements RecommendationResponse {

    @JsonProperty("value")
    private String value;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("recovery_suggestion")
    private String recoverySuggestion;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public String getRecoverySuggestion() {
        return recoverySuggestion;
    }

}
