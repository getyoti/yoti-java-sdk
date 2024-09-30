package com.yoti.api.client.docs.session.retrieve.identityprofile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileFailureResponse {

    @JsonProperty("reason_code")
    private String reasonCode;

    @JsonProperty("requirements_not_met_details")
    private List<RequirementNotMetDetailsResponse> requirementsNotMetDetails;

    public String getReasonCode() {
        return reasonCode;
    }

    public List<RequirementNotMetDetailsResponse> getRequirementsNotMetDetails() {
        return requirementsNotMetDetails;
    }

}
