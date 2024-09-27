package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileReportResponse {

    @JsonProperty("trust_framework")
    private String trustFramework;

    @JsonProperty("media")
    private MediaResponse media;

    @JsonProperty("schemes_compliance")
    private List<IdentityProfileSchemeComplianceReportResponse> schemesCompliance;

    /**
     * The trust framework the report was generated for
     *
     * @return the trust framework
     */
    public String getTrustFramework() {
        return trustFramework;
    }

    /**
     * The media object containing the report
     *
     * @return the report media
     */
    public MediaResponse getMedia() {
        return media;
    }

    /**
     * The list of schemes used in the trust framework
     *
     * @return the list of schemes
     */
    public List<IdentityProfileSchemeComplianceReportResponse> getSchemesCompliance() {
        return schemesCompliance;
    }

}
