package com.yoti.api.client.sandbox.docs.request.check;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
class SandboxDocumentCheck extends SandboxCheck {

    @JsonProperty("document_filter")
    private SandboxDocumentFilter documentFilter;

    SandboxDocumentCheck(SandboxCheckResult result, SandboxDocumentFilter documentFilter) {
        super(result);
        this.documentFilter = documentFilter;
    }

    public SandboxDocumentFilter getDocumentFilter() {
        return documentFilter;
    }

}
