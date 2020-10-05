package com.yoti.api.client.sandbox.docs.request.task;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxDocumentTextDataExtractionTaskResult {

    @JsonProperty("document_fields")
    private final Map<String, Object> documentFields;

    @JsonProperty("document_id_photo")
    private final SandboxDocumentIdPhoto documentIdPhoto;

    @JsonProperty("detected_country")
    private final String detectedCountry;

    @JsonProperty("recommendation")
    private final SandboxTextExtractionTaskRecommendation recommendation;

    SandboxDocumentTextDataExtractionTaskResult(Map<String, Object> documentFields,
            SandboxDocumentIdPhoto documentIdPhoto,
            String detectedCountry,
            SandboxTextExtractionTaskRecommendation recommendation) {
        this.documentFields = documentFields;
        this.documentIdPhoto = documentIdPhoto;
        this.detectedCountry = detectedCountry;
        this.recommendation = recommendation;
    }

    public Map<String, Object> getDocumentFields() {
        return documentFields;
    }

    public SandboxDocumentIdPhoto getDocumentIdPhoto() {
        return documentIdPhoto;
    }

    public String getDetectedCountry() {
        return detectedCountry;
    }

    public SandboxTextExtractionTaskRecommendation getRecommendation() {
        return recommendation;
    }
}
