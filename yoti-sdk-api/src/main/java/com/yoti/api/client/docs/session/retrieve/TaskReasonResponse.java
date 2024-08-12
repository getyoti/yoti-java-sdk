package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskReasonResponse {

    @JsonProperty("value")
    private String value;

    @JsonProperty("detail")
    private String detail;

    /**
     * The reason for the recommendation
     *
     * @return the reason value
     */
    public String getValue() {
        return value;
    }

    /**
     * The fine-grained details for the reason of the recommendation
     *
     * @return the details
     */
    public String getDetail() {
        return detail;
    }

}
