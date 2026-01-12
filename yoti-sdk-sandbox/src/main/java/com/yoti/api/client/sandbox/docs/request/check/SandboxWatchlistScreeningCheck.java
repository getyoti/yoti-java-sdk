package com.yoti.api.client.sandbox.docs.request.check;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxWatchlistScreeningCheck extends SandboxCheck {

    SandboxWatchlistScreeningCheck(SandboxCheckResult result) {
        super(result);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SandboxWatchlistScreeningCheck}
     */
    public static class Builder extends SandboxCheck.Builder<Builder> {

        private Builder() {}

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SandboxWatchlistScreeningCheck build() {
            SandboxCheckReport report = recommendation == null && breakdown == null
                    ? null
                    : new SandboxCheckReport(recommendation, breakdown);
            SandboxCheckResult result = new SandboxCheckResult(report, reportTemplate);

            return new SandboxWatchlistScreeningCheck(result);
        }

    }
}
