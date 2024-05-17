package com.yoti.api.client.docs.session.retrieve.identityprofile.advanced;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfileTrustFrameworkResponse {

    @JsonProperty("trust_framework")
    private String trustFramework;

    @JsonProperty("schemes_compliance")
    private List<AdvancedIdentityProfileSchemeComplianceReportResponse> schemesCompliance;

    /**
     * Gets the trust framework for the report
     *
     * @return the trust framework
     */
    public String getTrustFramework() {
        return trustFramework;
    }

    /**
     * Gets the list of schemes that are part of the trust framework report
     *
     * @return the scheme compliances
     */
    public List<AdvancedIdentityProfileSchemeComplianceReportResponse> getSchemesCompliance() {
        return schemesCompliance;
    }

}
