package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileFailureResponse {

    @JsonProperty("reason_code")
    private String reasonCode;

    public String getReasonCode() {
        return reasonCode;
    }

}
