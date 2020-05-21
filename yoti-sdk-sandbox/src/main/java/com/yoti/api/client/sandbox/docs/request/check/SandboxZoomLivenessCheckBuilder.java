package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxZoomLivenessCheckBuilder extends SandboxCheckBuilder<SandboxZoomLivenessCheckBuilder> {

    SandboxZoomLivenessCheckBuilder() {}

    @Override
    protected SandboxZoomLivenessCheckBuilder self() {
        return this;
    }

    @Override
    public SandboxLivenessCheck build() {
        notNull(recommendation, "recommendation");

        SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
        SandboxCheckResult result = new SandboxCheckResult(report);

        return new SandboxLivenessCheck(result, DocScanConstants.ZOOM);
    }

}
