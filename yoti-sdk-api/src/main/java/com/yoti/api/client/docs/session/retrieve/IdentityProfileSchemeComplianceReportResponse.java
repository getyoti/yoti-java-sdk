package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileSchemeComplianceReportResponse {

    @JsonProperty("scheme")
    private IdentityProfileSchemeResponse scheme;

    @JsonProperty("requirements_met")
    private Boolean requirementsMet;

    @JsonProperty("requirements_not_met_info")
    private String requirementsNotMetInfo;

    /**
     * The Identity Profile scheme
     *
     * @return the scheme
     */
    public IdentityProfileSchemeResponse getScheme() {
        return scheme;
    }

    /**
     * Whether or not the requirements for the scheme were met
     *
     * @return boolean
     */
    public Boolean getRequirementsMet() {
        return requirementsMet;
    }

    /**
     * Information about why the requirements for the scheme were not met
     *
     * @return string
     */
    public String getRequirementsNotMetInfo() {
        return requirementsNotMetInfo;
    }

}
