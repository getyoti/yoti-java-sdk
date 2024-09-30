package com.yoti.api.client.docs.session.retrieve.identityprofile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileResponse {

    @JsonProperty("subject_id")
    private String subjectId;

    @JsonProperty("result")
    private String result;

    @JsonProperty("failure_reason")
    private IdentityProfileFailureResponse failureReason;

    @JsonProperty("identity_profile_report")
    private IdentityProfileReportResponse identityProfileReport;

    public String getSubjectId() {
        return subjectId;
    }

    public String getResult() {
        return result;
    }

    public IdentityProfileFailureResponse getFailureReason() {
        return failureReason;
    }

    public IdentityProfileReportResponse getIdentityProfileReport() {
        return identityProfileReport;
    }

}
