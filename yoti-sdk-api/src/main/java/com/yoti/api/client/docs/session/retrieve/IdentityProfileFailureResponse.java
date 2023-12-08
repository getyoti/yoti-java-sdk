package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileFailureResponse {

    @JsonProperty("reason_code")
    private String reasonCode;

    @JsonProperty("requirement_not_met_details")
    private RequirementNotMetDetailsResponse requirementNotMetDetails;

    public String getReasonCode() {
        return reasonCode;
    }

    public RequirementNotMetDetailsResponse getRequirementNotMetDetails() {
        return requirementNotMetDetails;
    }

}
