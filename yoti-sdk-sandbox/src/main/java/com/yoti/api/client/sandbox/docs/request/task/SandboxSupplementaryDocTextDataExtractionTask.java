package com.yoti.api.client.sandbox.docs.request.task;

import static com.yoti.validation.Validation.notNull;

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

    @JsonProperty("response_delay")
    private final Integer responseDelay;

    SandboxSupplementaryDocTextDataExtractionTask(SandboxSupplementaryDocTextDataExtractionTaskResult result,
            SandboxDocumentFilter documentFilter,
            Integer responseDelay) {
        this.result = result;
        this.documentFilter = documentFilter;
        this.responseDelay = responseDelay;
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

    public Integer getResponseDelay() {
        return responseDelay;
    }

    /**
     * Builder for {@link SandboxSupplementaryDocTextDataExtractionTask}
     */
    public static class Builder {

        private Map<String, Object> documentFields;
        private SandboxDocumentFilter documentFilter;
        private String detectedCountry;
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

        public Builder withDetectedCountry(String detectedCountry) {
            this.detectedCountry = detectedCountry;
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

        public SandboxSupplementaryDocTextDataExtractionTask build() {
            SandboxSupplementaryDocTextDataExtractionTaskResult result = new SandboxSupplementaryDocTextDataExtractionTaskResult(documentFields,
                    detectedCountry, recommendation);
            return new SandboxSupplementaryDocTextDataExtractionTask(result, documentFilter, responseDelay);
        }
    }
}
