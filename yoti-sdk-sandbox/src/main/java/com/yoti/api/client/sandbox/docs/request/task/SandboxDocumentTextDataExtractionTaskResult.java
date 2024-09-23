package com.yoti.api.client.sandbox.docs.request.task;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SandboxDocumentTextDataExtractionTaskResult {

    @JsonProperty("document_fields")
    private final Map<String, Object> documentFields;

    @JsonProperty("document_id_photo")
    private final SandboxDocumentIdPhoto documentIdPhoto;

    @JsonProperty("detected_country")
    private final String detectedCountry;

    @JsonProperty("detected_document_type")
    private final String detectedDocumentType;

    @JsonProperty("recommendation")
    private final SandboxTextExtractionTaskRecommendation recommendation;

    SandboxDocumentTextDataExtractionTaskResult(Map<String, Object> documentFields,
            SandboxDocumentIdPhoto documentIdPhoto,
            String detectedCountry,
            String detectedDocumentType,
            SandboxTextExtractionTaskRecommendation recommendation) {
        this.documentFields = documentFields;
        this.documentIdPhoto = documentIdPhoto;
        this.detectedCountry = detectedCountry;
        this.detectedDocumentType = detectedDocumentType;
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

    public String getDetectedDocumentType() {
        return detectedDocumentType;
    }

    public SandboxTextExtractionTaskRecommendation getRecommendation() {
        return recommendation;
    }
}
