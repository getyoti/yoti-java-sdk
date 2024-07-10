package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxStaticLivenessCheckBuilder extends SandboxCheck.Builder<SandboxStaticLivenessCheckBuilder> {

    SandboxStaticLivenessCheckBuilder() {
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

        return new SandboxLivenessCheck(result, DocScanConstants.STATIC);
    }

}
