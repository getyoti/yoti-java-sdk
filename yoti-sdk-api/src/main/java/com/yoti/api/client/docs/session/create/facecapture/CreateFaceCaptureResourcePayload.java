package com.yoti.api.client.docs.session.create.facecapture;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFaceCaptureResourcePayload {

    @JsonProperty("requirement_id")
    private final String requirementId;

    private CreateFaceCaptureResourcePayload(String requirementId) {
        this.requirementId = requirementId;
    }

    public static CreateFaceCaptureResourcePayload.Builder builder() {
        return new CreateFaceCaptureResourcePayload.Builder();
    }

    public String getRequirementId() {
        return requirementId;
    }

    public static class Builder {

        private String requirementId;

        private Builder() { }

        /**
         * Sets the id of the requirement that the resource will be used to satisfy.
         *
         * @param requirementId the requirement ID
         * @return the builder
         */
        public Builder withRequirementId(String requirementId) {
            this.requirementId = requirementId;
            return this;
        }

        public CreateFaceCaptureResourcePayload build() {
            return new CreateFaceCaptureResourcePayload(requirementId);
        }

    }

}
