package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxFaceComparisonCheck extends SandboxCheck {

    SandboxFaceComparisonCheck(SandboxCheckResult result) {
        super(result);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SandboxFaceComparisonCheck}
     */
    public static class Builder extends SandboxCheck.Builder<Builder> {

        private Builder() {}

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SandboxFaceComparisonCheck build() {
            SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
            SandboxCheckResult result = new SandboxCheckResult(report, reportTemplate);

            return new SandboxFaceComparisonCheck(result);
        }

    }
}
