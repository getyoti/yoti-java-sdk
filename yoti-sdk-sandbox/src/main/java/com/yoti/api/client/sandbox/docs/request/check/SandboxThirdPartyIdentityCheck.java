package com.yoti.api.client.sandbox.docs.request.check;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxThirdPartyIdentityCheck extends SandboxCheck {

    SandboxThirdPartyIdentityCheck(SandboxCheckResult result) {
        super(result);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SandboxThirdPartyIdentityCheck}
     */
    public static class Builder extends SandboxCheck.Builder<Builder> {

        private Builder() {
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SandboxThirdPartyIdentityCheck build() {
            SandboxCheckReport report = recommendation == null && breakdown == null
                    ? null
                    : new SandboxCheckReport(recommendation, breakdown);
            SandboxCheckResult result = new SandboxCheckResult(report, reportTemplate);

            return new SandboxThirdPartyIdentityCheck(result);
        }

    }
}
