package com.yoti.api.client.sandbox.docs.request.task;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxSupplementaryDocTextDataExtractionTaskResult {

    @JsonProperty("document_fields")
    private final Map<String, Object> documentFields;

    @JsonProperty("detected_country")
    private final String detectedCountry;

    @JsonProperty("recommendation")
    private final SandboxTextExtractionTaskRecommendation recommendation;

    SandboxSupplementaryDocTextDataExtractionTaskResult(Map<String, Object> documentFields,
            String detectedCountry,
            SandboxTextExtractionTaskRecommendation recommendation) {
        this.documentFields = documentFields;
        this.detectedCountry = detectedCountry;
        this.recommendation = recommendation;
    }

    public Map<String, Object> getDocumentFields() {
        return documentFields;
    }

    public String getDetectedCountry() {
        return detectedCountry;
    }

    public SandboxTextExtractionTaskRecommendation getRecommendation() {
        return recommendation;
    }

}
