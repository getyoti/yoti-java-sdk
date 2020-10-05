package com.yoti.api.client.sandbox.docs.request.task;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxDocumentTextDataExtractionTaskResult {

    @JsonProperty("document_fields")
    private final Map<String, Object> documentFields;

    @JsonProperty("document_id_photo")
    private final SandboxDocumentIdPhoto documentIdPhoto;

    SandboxDocumentTextDataExtractionTaskResult(Map<String, Object> documentFields, SandboxDocumentIdPhoto documentIdPhoto) {
        this.documentFields = documentFields;
        this.documentIdPhoto = documentIdPhoto;
    }

    public Map<String, Object> getDocumentFields() {
        return documentFields;
    }

    public SandboxDocumentIdPhoto getDocumentIdPhoto() {
        return documentIdPhoto;
    }
}
