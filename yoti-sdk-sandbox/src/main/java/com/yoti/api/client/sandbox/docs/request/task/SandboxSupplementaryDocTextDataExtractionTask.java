package com.yoti.api.client.sandbox.docs.request.task;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SandboxSupplementaryDocTextDataExtractionTask {

    @JsonProperty("result")
    private final SandboxSupplementaryDocTextDataExtractionTaskResult result;

    @JsonProperty("document_filter")
    private final SandboxDocumentFilter documentFilter;

    SandboxSupplementaryDocTextDataExtractionTask(SandboxSupplementaryDocTextDataExtractionTaskResult result, SandboxDocumentFilter documentFilter) {
        this.result = result;
        this.documentFilter = documentFilter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public SandboxSupplementaryDocTextDataExtractionTaskResult getResult() {
        return result;
    }

    public SandboxDocumentFilter getDocumentFilter() {
        return documentFilter;
    }

    /**
     * Builder for {@link SandboxSupplementaryDocTextDataExtractionTask}
     */
    public static class Builder {

        private Map<String, Object> documentFields;
        private SandboxDocumentFilter documentFilter;
        private String detectedCountry;
        private SandboxTextExtractionTaskRecommendation recommendation;

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

        public Builder withDetectedCountry(String detectedCountry) {
            this.detectedCountry = detectedCountry;
            return this;
        }

        public Builder withRecommendation(SandboxTextExtractionTaskRecommendation recommendation) {
            this.recommendation = recommendation;
            return this;
        }

        public SandboxSupplementaryDocTextDataExtractionTask build() {
            SandboxSupplementaryDocTextDataExtractionTaskResult result = new SandboxSupplementaryDocTextDataExtractionTaskResult(documentFields,
                    detectedCountry, recommendation);
            return new SandboxSupplementaryDocTextDataExtractionTask(result, documentFilter);
        }
    }
}