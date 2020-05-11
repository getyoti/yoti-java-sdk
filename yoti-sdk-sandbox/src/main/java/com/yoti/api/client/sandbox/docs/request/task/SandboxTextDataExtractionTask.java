package com.yoti.api.client.sandbox.docs.request.task;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SandboxTextDataExtractionTask {

    @JsonProperty("result")
    private final SandboxTextDataExtractionTaskResult result;

    @JsonProperty("document_filter")
    private final SandboxDocumentFilter documentFilter;

    SandboxTextDataExtractionTask(SandboxTextDataExtractionTaskResult result, SandboxDocumentFilter documentFilter) {
        this.result = result;
        this.documentFilter = documentFilter;
    }

    public static Builder builder() {
        return new Builder();
    }

    public SandboxTextDataExtractionTaskResult getResult() {
        return result;
    }

    public SandboxDocumentFilter getDocumentFilter() {
        return documentFilter;
    }

    /**
     * Builder for {@link SandboxTextDataExtractionTask}
     */
    public static class Builder {

        private Map<String, Object> documentFields;
        private SandboxDocumentFilter documentFilter;

        public Builder withDocumentField(String key, Object value) {
            if (documentFields == null) {
                documentFields = new HashMap<>();
            }

            documentFields.put(key, value);
            return this;
        }

        public Builder withDocumentFields(Map<String, Object> documentFields) {
            this.documentFields = documentFields;
            return this;
        }

        public Builder withDocumentFilter(SandboxDocumentFilter documentFilter) {
            this.documentFilter = documentFilter;
            return this;
        }

        public SandboxTextDataExtractionTask build() {
            notNull(documentFields, "documentFields");

            SandboxTextDataExtractionTaskResult result = new SandboxTextDataExtractionTaskResult(documentFields);
            return new SandboxTextDataExtractionTask(result, documentFilter);
        }

    }
}
