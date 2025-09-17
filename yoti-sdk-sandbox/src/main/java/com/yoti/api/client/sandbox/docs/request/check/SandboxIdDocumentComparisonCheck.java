package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxIdDocumentComparisonCheck extends SandboxCheck {

    @JsonProperty("secondary_document_filter")
    private final SandboxDocumentFilter secondaryDocumentFilter;

    SandboxIdDocumentComparisonCheck(SandboxCheckResult result, SandboxDocumentFilter secondaryDocumentFilter) {
        super(result);
        this.secondaryDocumentFilter = secondaryDocumentFilter;
    }

    public SandboxDocumentFilter getSecondaryDocumentFilter() {
        return secondaryDocumentFilter;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder extends SandboxCheck.Builder<Builder> {

        private SandboxDocumentFilter secondaryDocumentFilter;

        private Builder() {}

        public Builder withSecondaryDocumentFilter(SandboxDocumentFilter secondaryDocumentFilter) {
            this.secondaryDocumentFilter = secondaryDocumentFilter;
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SandboxIdDocumentComparisonCheck build() {
            SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
            SandboxCheckResult result = new SandboxCheckResult(report, reportTemplate);

            return new SandboxIdDocumentComparisonCheck(result, secondaryDocumentFilter);
        }
    }

}
