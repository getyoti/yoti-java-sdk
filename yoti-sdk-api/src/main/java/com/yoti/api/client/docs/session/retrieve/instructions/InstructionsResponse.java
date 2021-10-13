package com.yoti.api.client.docs.session.retrieve.instructions;

import java.util.List;

import com.yoti.api.client.docs.session.retrieve.instructions.branch.BranchResponse;
import com.yoti.api.client.docs.session.retrieve.instructions.document.DocumentProposalResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstructionsResponse {

    @JsonProperty("contact_profile_exists")
    private boolean contactProfileExists;

    @JsonProperty("documents")
    private List<DocumentProposalResponse> documents;

    @JsonProperty("branch")
    private BranchResponse branch;

    public boolean isContactProfileExists() {
        return contactProfileExists;
    }

    public List<DocumentProposalResponse> getDocuments() {
        return documents;
    }

    public BranchResponse getBranch() {
        return branch;
    }

}
