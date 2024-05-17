package com.yoti.api.client.docs.session.retrieve.identityprofile.advanced;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfileSchemeComplianceReportResponse {

    @JsonProperty("scheme")
    private AdvancedIdentityProfileSchemeResponse scheme;

    @JsonProperty("requirements_met")
    private Boolean requirementsMet;

    @JsonProperty("requirements_not_met_info")
    private String requirementsNotMetInfo;

    public AdvancedIdentityProfileSchemeResponse getScheme() {
        return scheme;
    }

    public Boolean getRequirementsMet() {
        return requirementsMet;
    }

    public String getRequirementsNotMetInfo() {
        return requirementsNotMetInfo;
    }

}
