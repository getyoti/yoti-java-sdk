package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.validation.Validation.notNull;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxStaticLivenessCheckBuilder extends SandboxCheck.Builder<SandboxStaticLivenessCheckBuilder> {

    private Integer responseDelay;

    SandboxStaticLivenessCheckBuilder() {}

    public SandboxStaticLivenessCheckBuilder withResponseDelay(Integer responseDelay) {
        this.responseDelay = responseDelay;
        return this;
    }

    @Override
    protected SandboxStaticLivenessCheckBuilder self() {
        return this;
    }

    @Override
    public SandboxLivenessCheck build() {
        notNull(recommendation, "recommendation");

        SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
        SandboxCheckResult result = new SandboxCheckResult(report);

        return new SandboxLivenessCheck(result, DocScanConstants.STATIC, responseDelay);
    }

}
