package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentityProfileSubjectPayload {

    @JsonProperty("subject_id")
    private final String subjectId;

    IdentityProfileSubjectPayload(String subjectId) {
        this.subjectId = subjectId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSubjectId() {
        return subjectId;
    }

    public static class Builder {

        private String subjectId;

        Builder() {}

        /**
         * Sets the subject ID for the Identity Profile requirement
         *
         * @param subjectId the subject ID
         * @return the builder
         */
        public Builder withSubjectId(String subjectId) {
            this.subjectId = subjectId;
            return this;
        }

        public IdentityProfileSubjectPayload build() {
            return new IdentityProfileSubjectPayload(subjectId);
        }

    }

}
