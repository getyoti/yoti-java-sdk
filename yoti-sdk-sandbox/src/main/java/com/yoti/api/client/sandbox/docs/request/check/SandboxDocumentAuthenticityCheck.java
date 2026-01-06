package com.yoti.api.client.sandbox.docs.request.check;

import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxDocumentAuthenticityCheck extends SandboxDocumentCheck {

    private SandboxDocumentAuthenticityCheck(SandboxCheckResult result, SandboxDocumentFilter documentFilter) {
        super(result, documentFilter);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SandboxDocumentAuthenticityCheck}
     */
    public static class Builder extends SandboxDocumentCheck.Builder<Builder> {

        private Builder() {
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SandboxDocumentAuthenticityCheck build() {
            SandboxCheckReport report = recommendation == null && breakdown == null
                    ? null
                    : new SandboxCheckReport(recommendation, breakdown);
            SandboxCheckResult result = new SandboxCheckResult(report, reportTemplate);

            return new SandboxDocumentAuthenticityCheck(result, documentFilter);
        }

    }
}
