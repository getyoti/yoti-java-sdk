package com.yoti.api.client.sandbox.docs.request;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.sandbox.docs.request.task.SandboxDocumentTextDataExtractionTask;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SandboxTaskResults {

    @JsonProperty(DocScanConstants.ID_DOCUMENT_TEXT_DATA_EXTRACTION)
    private final List<SandboxDocumentTextDataExtractionTask> documentTextDataExtractionTasks;

    SandboxTaskResults(List<SandboxDocumentTextDataExtractionTask> documentTextDataExtractionTasks) {
        this.documentTextDataExtractionTasks = documentTextDataExtractionTasks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<SandboxDocumentTextDataExtractionTask> getDocumentTextDataExtractionTasks() {
        return documentTextDataExtractionTasks;
    }

    /**
     * Builder for {@link SandboxTaskResults}
     */
    public static class Builder {

        private List<SandboxDocumentTextDataExtractionTask> documentTextDataExtractionTasks = new ArrayList<>();

        private Builder() {}

        public Builder withDocumentTextDataExtractionTask(SandboxDocumentTextDataExtractionTask textDataExtractionTask) {
            this.documentTextDataExtractionTasks.add(textDataExtractionTask);
            return this;
        }

        public SandboxTaskResults build() {
            return new SandboxTaskResults(documentTextDataExtractionTasks);
        }
    }
}
