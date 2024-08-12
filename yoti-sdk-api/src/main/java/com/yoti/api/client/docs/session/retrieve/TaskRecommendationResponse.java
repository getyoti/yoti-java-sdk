package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskRecommendationResponse {

    @JsonProperty("value")
    private String value;

    @JsonProperty("reason")
    private TaskReasonResponse reason;

    /**
     * The recommendation value
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * The reason for the recommendation
     *
     * @return the reason
     */
    public TaskReasonResponse getReason() {
        return reason;
    }

}
