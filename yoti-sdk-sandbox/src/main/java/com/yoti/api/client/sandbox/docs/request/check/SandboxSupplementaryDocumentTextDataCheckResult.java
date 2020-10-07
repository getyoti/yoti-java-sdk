package com.yoti.api.client.sandbox.docs.request.check;

import java.util.Map;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxSupplementaryDocumentTextDataCheckResult extends SandboxCheckResult {

    @JsonProperty("document_fields")
    private Map<String, Object> documentFields;

    SandboxSupplementaryDocumentTextDataCheckResult(SandboxCheckReport report, Map<String, Object> documentFields) {
        super(report);
        this.documentFields = documentFields;
    }

    public Map<String, Object> getDocumentFields() {
        return documentFields;
    }
}
