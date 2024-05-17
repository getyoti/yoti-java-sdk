package com.yoti.api.client.docs.session.retrieve.identityprofile.advanced;

import com.yoti.api.client.docs.session.retrieve.IdentityProfileFailureResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedIdentityProfileResponse {

    @JsonProperty("subject_id")
    private String subjectId;

    @JsonProperty("result")
    private String result;

    @JsonProperty("failure_reason")
    private IdentityProfileFailureResponse failureReason;

    @JsonProperty("identity_profile_report")
    private AdvancedIdentityProfileReportResponse identityProfileReport;

    /**
     * Returns the subject ID of the Advanced Identity Profile
     *
     * @return the subject ID
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * Returns the result of the Advanced Identity Profile
     *
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * Returns the failure reason of the Advanced Identity Profile
     *
     * @return the failure reason
     */
    public IdentityProfileFailureResponse getFailureReason() {
        return failureReason;
    }

    /**
     * Returns the report for the Advanced Identity Profile
     *
     * @return the report
     */
    public AdvancedIdentityProfileReportResponse getIdentityProfileReport() {
        return identityProfileReport;
    }

}
