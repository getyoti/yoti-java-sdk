package com.yoti.api.client.sandbox.docs.request;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.sandbox.docs.request.task.SandboxTextDataExtractionTask;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SandboxTaskResults {

    @JsonProperty(DocScanConstants.ID_DOCUMENT_TEXT_DATA_EXTRACTION)
    private final List<SandboxTextDataExtractionTask> textDataExtractionTasks;

    SandboxTaskResults(List<SandboxTextDataExtractionTask> textDataExtractionTasks) {
        this.textDataExtractionTasks = textDataExtractionTasks;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<SandboxTextDataExtractionTask> getTextDataExtractionTasks() {
        return textDataExtractionTasks;
    }

    /**
     * Builder for {@link SandboxTaskResults}
     */
    public static class Builder {

        private List<SandboxTextDataExtractionTask> textDataExtractionTasks = new ArrayList<>();

        public Builder withTextDataExtractionTask(SandboxTextDataExtractionTask textDataExtractionTask) {
            this.textDataExtractionTasks.add(textDataExtractionTask);
            return this;
        }

        public SandboxTaskResults build() {
            return new SandboxTaskResults(textDataExtractionTasks);
        }
    }
}
