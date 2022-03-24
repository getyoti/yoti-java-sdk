package com.yoti.api.client.shareurl.policy.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Holds information about the subject for which the identity assertion will be performed.
 */
public final class Subject {

    @JsonProperty(Property.SUBJECT_ID)
    private final String subjectId;

    Subject(String subjectId) {
        this.subjectId = subjectId;
    }

    public static Subject.Builder builder() {
        return new Subject.Builder();
    }

    /**
     * An ID of the subject that will be returned into the verification report.
     *
     * @return subjectId
     */
    public String getSubjectId() {
        return subjectId;
    }

    public static final class Builder {

        private String subjectId;

        private Builder() { }

        public Builder withSubjectId(String subjectId) {
            this.subjectId = subjectId;
            return this;
        }

        public Subject build() {
            return new Subject(subjectId);
        }

    }

    private static final class Property {

        private Property() { }

        private static final String SUBJECT_ID = "subject_id";

    }

}
