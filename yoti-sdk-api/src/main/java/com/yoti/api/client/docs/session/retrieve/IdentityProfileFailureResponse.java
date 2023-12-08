package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileFailureResponse {

    @JsonProperty("reason_code")
    private String reasonCode;

    @JsonProperty("requirement_not_met_details")
    private List<RequirementNotMetDetailsResponse> requirementNotMetDetails;

    public String getReasonCode() {
        return reasonCode;
    }

    public List<RequirementNotMetDetailsResponse> getRequirementNotMetDetails() {
        return requirementNotMetDetails;
    }

}
