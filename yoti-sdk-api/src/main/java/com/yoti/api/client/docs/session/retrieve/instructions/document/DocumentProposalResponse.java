package com.yoti.api.client.docs.session.retrieve.instructions.document;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentProposalResponse {

    @JsonProperty("requirement_id")
    private String requirementId;

    @JsonProperty("document")
    private SelectedDocumentResponse document;

    public String getRequirementId() {
        return requirementId;
    }

    public SelectedDocumentResponse getDocument() {
        return document;
    }

}
