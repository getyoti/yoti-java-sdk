package com.yoti.api.client.docs.session.create.resources;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Container for creating resources at session creation.
 */
public class ResourceCreationContainer {

    private static final String APPLICANT_PROFILE = "applicant_profile";

    @JsonProperty(APPLICANT_PROFILE)
    private final Map<String, Object> applicantProfile;

    private ResourceCreationContainer(Map<String, Object> applicantProfile) {
        this.applicantProfile = applicantProfile;
    }

    public static ResourceCreationContainer.Builder builder() {
        return new ResourceCreationContainer.Builder();
    }

    /**
     * The applicant profile resource to be created.
     *
     * @return the applicant profile fields
     */
    public Map<String, Object> getApplicantProfile() {
        return applicantProfile;
    }

    public static final class Builder {

        private Map<String, Object> applicantProfile;

        private Builder() {}

        public Builder withApplicantProfile(Map<String, Object> applicantProfile) {
            this.applicantProfile = applicantProfile;
            return this;
        }

        public ResourceCreationContainer build() {
            return new ResourceCreationContainer(applicantProfile);
        }

    }

}
