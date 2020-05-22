package com.yoti.api.client.sandbox.docs.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseConfig {

    @JsonProperty("task_results")
    private SandboxTaskResults taskResults;

    @JsonProperty("check_reports")
    private SandboxCheckReports checkReports;

    ResponseConfig(SandboxTaskResults taskResults, SandboxCheckReports checkReports) {
        this.taskResults = taskResults;
        this.checkReports = checkReports;
    }

    public static Builder builder() {
        return new Builder();
    }

    public SandboxTaskResults getTaskResults() {
        return taskResults;
    }

    public SandboxCheckReports getCheckReports() {
        return checkReports;
    }

    /**
     * Builder for {@link ResponseConfig}
     */
    public static class Builder {

        private SandboxTaskResults taskResults;

        private SandboxCheckReports sandboxCheckReports;

        public Builder withTaskResults(SandboxTaskResults taskResults) {
            this.taskResults = taskResults;
            return this;
        }

        public Builder withCheckReport(SandboxCheckReports sandboxCheckReports) {
            this.sandboxCheckReports = sandboxCheckReports;
            return this;
        }

        public ResponseConfig build() {
            return new ResponseConfig(taskResults, sandboxCheckReports);
        }

    }

}
