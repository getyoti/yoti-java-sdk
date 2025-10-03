package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxSynecticsIdentityFraudCheck extends SandboxCheck {

    SandboxSynecticsIdentityFraudCheck(SandboxCheckResult result) {
        super(result);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SandboxSynecticsIdentityFraudCheck}
     */
    public static class Builder extends SandboxCheck.Builder<Builder> {

        private Builder() {}

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SandboxSynecticsIdentityFraudCheck build() {
            SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
            SandboxCheckResult result = new SandboxCheckResult(report, reportTemplate);

            return new SandboxSynecticsIdentityFraudCheck(result);
        }

    }
}
