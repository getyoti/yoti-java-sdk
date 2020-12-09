package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

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
            notNull(recommendation, "recommendation");

            SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
            SandboxCheckResult result = new SandboxCheckResult(report);

            return new SandboxThirdPartyIdentityCheck(result);
        }

    }
}
