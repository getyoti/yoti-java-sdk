package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileResponse {

    @JsonProperty(Property.SUBJECT_ID)
    private String subjectId;

    @JsonProperty(Property.RESULT)
    private String result;

    @JsonProperty(Property.FAILURE_REASON)
    private IdentityProfileFailureResponse failureReason;

    @JsonProperty(Property.IDENTITY_PROFILE_REPORT)
    private Object identityProfileReport;

    public String getSubjectId() {
        return subjectId;
    }

    public String getResult() {
        return result;
    }

    public IdentityProfileFailureResponse getFailureReason() {
        return failureReason;
    }

    public Object getIdentityProfileReport() {
        return identityProfileReport;
    }

    private static final class Property {

        private static final String SUBJECT_ID = "subject_id";
        private static final String RESULT = "result";
        private static final String FAILURE_REASON = "failure_reason";
        private static final String IDENTITY_PROFILE_REPORT = "identity_profile_report";

        private Property() { }

    }

}
