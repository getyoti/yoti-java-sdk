package com.yoti.api.client.docs.session.instructions.document;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentProposal {

    @JsonProperty("requirement_id")
    private final String requirementId;

    @JsonProperty("document")
    private final SelectedDocument document;

    private DocumentProposal(String requirementId, SelectedDocument document) {
        this.requirementId = requirementId;
        this.document = document;
    }

    public static DocumentProposal.Builder builder() {
        return new DocumentProposal.Builder();
    }

    public String getRequirementId() {
        return requirementId;
    }

    public SelectedDocument getDocument() {
        return document;
    }

    public static class Builder {

        private String requirementId;
        private SelectedDocument document;

        private Builder() {}

        /**
         * Sets the requirementId that the document proposal will satisfy
         *
         * @param requirementId the requirement ID
         * @return the builder
         */
        public Builder withRequirementId(String requirementId) {
            this.requirementId = requirementId;
            return this;
        }

        /**
         * Sets the {@link SelectedDocument} that will be used to satisfy the requirement
         *
         * @param document the selected document
         * @return the builder
         */
        public Builder withSelectedDocument(SelectedDocument document) {
            this.document = document;
            return this;
        }

        public DocumentProposal build() {
            return new DocumentProposal(requirementId, document);
        }

    }

}
