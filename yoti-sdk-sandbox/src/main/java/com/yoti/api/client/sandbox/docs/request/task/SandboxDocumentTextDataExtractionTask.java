package com.yoti.api.client.sandbox.docs.request.task;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

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

    SandboxDocumentTextDataExtractionTask(SandboxDocumentTextDataExtractionTaskResult result, SandboxDocumentFilter documentFilter) {
        this.result = result;
        this.documentFilter = documentFilter;
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

    /**
     * Builder for {@link SandboxDocumentTextDataExtractionTask}
     */
    public static class Builder {

        private Map<String, Object> documentFields;
        private SandboxDocumentFilter documentFilter;

        private Builder() {
        }

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

        public SandboxDocumentTextDataExtractionTask build() {
            SandboxDocumentTextDataExtractionTaskResult result = new SandboxDocumentTextDataExtractionTaskResult(documentFields);
            return new SandboxDocumentTextDataExtractionTask(result, documentFilter);
        }

    }
}
