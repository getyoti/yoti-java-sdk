package com.yoti.api.client.sandbox.docs.request.task;

import static com.yoti.validation.Validation.notNull;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SandboxDocumentTextDataExtractionTask {

    @JsonProperty("result")
    private final SandboxDocumentTextDataExtractionTaskResult result;

    @JsonProperty("document_filter")
    private final SandboxDocumentFilter documentFilter;

    @JsonProperty("response_delay")
    private final Integer responseDelay;

    SandboxDocumentTextDataExtractionTask(SandboxDocumentTextDataExtractionTaskResult result, SandboxDocumentFilter documentFilter, Integer responseDelay) {
        this.result = result;
        this.documentFilter = documentFilter;
        this.responseDelay = responseDelay;
    }

    public static Builder builder() {
        return new Builder();
    }

    public SandboxDocumentTextDataExtractionTaskResult getResult() {
        return result;
    }

    public SandboxDocumentFilter getDocumentFilter() {
        return documentFilter;
    }

    public Integer getResponseDelay() {
        return responseDelay;
    }

    /**
     * Builder for {@link SandboxDocumentTextDataExtractionTask}
     */
    public static class Builder {

        private Map<String, Object> documentFields;
        private SandboxDocumentFilter documentFilter;
        private SandboxDocumentIdPhoto documentIdPhoto;
        private String detectedCountry;
        private String detectedDocumentType;
        private SandboxTextExtractionTaskRecommendation recommendation;
        private Integer responseDelay;

        private Builder() {}

        public Builder withDocumentField(String key, Object value) {
            if (documentFields == null) {
                documentFields = new HashMap<>();
            }

            documentFields.put(key, value);
            return this;
        }

        public Builder withDocumentFields(Map<String, Object> documentFields) {
            notNull(documentFields, "documentFields");
            this.documentFields = documentFields;
            return this;
        }

        public Builder withDocumentFilter(SandboxDocumentFilter documentFilter) {
            this.documentFilter = documentFilter;
            return this;
        }

        public Builder withDocumentIdPhoto(String contentType, byte[] documentIdPhoto) {
            String b64DocumentIdPhoto = Base64.getEncoder().encodeToString(documentIdPhoto);
            this.documentIdPhoto = new SandboxDocumentIdPhoto(contentType, b64DocumentIdPhoto);
            return this;
        }

        public Builder withDetectedCountry(String detectedCountry) {
            this.detectedCountry = detectedCountry;
            return this;
        }

        public Builder withDetectedDocumentType(String detectedDocumentType) {
            this.detectedDocumentType = detectedDocumentType;
            return this;
        }

        public Builder withRecommendation(SandboxTextExtractionTaskRecommendation recommendation) {
            this.recommendation = recommendation;
            return this;
        }

        public Builder withResponseDelay(Integer responseDelay) {
            this.responseDelay = responseDelay;
            return this;
        }

        public SandboxDocumentTextDataExtractionTask build() {
            SandboxDocumentTextDataExtractionTaskResult result = new SandboxDocumentTextDataExtractionTaskResult(documentFields,
                    documentIdPhoto,
                    detectedCountry,
                    detectedDocumentType,
                    recommendation);
            return new SandboxDocumentTextDataExtractionTask(result, documentFilter, responseDelay);
        }
    }
}
