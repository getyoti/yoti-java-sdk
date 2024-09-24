package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.validation.Validation.notNull;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

public class SandboxZoomLivenessCheckBuilder extends SandboxCheck.Builder<SandboxZoomLivenessCheckBuilder> {

    private Integer responseDelay;

    SandboxZoomLivenessCheckBuilder() {}

    @Override
    protected SandboxZoomLivenessCheckBuilder self() {
        return this;
    }

    public SandboxZoomLivenessCheckBuilder withResponseDelay(Integer responseDelay) {
        this.responseDelay = responseDelay;
        return this;
    }

    @Override
    public SandboxLivenessCheck build() {
        notNull(recommendation, "recommendation");

        SandboxCheckReport report = new SandboxCheckReport(recommendation, breakdown);
        SandboxCheckResult result = new SandboxCheckResult(report);

        return new SandboxLivenessCheck(result, DocScanConstants.ZOOM, responseDelay);
    }

}
