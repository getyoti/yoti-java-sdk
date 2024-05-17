package com.yoti.api.client.docs.session.retrieve.identityprofile.advanced;

import java.util.List;

import com.yoti.api.client.docs.session.retrieve.MediaResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfileReportResponse {

    @JsonProperty("compliance")
    private List<AdvancedIdentityProfileTrustFrameworkResponse> compliance;

    @JsonProperty("media")
    private MediaResponse media;

    /**
     * Gets the compliance reports for the advanced identity profile
     *
     * @return the compliance reports
     */
    public List<AdvancedIdentityProfileTrustFrameworkResponse> getCompliance() {
        return compliance;
    }

    /**
     * Gets the media for the generated profile of the advanced identity profile
     *
     * @return the generated media
     */
    public MediaResponse getMedia() {
        return media;
    }

}
