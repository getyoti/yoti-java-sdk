package com.yoti.api.client.sandbox.docs.request.check;

import com.fasterxml.jackson.annotation.JsonProperty;

class SandboxCheckResult {

    @JsonProperty("report")
    private final SandboxCheckReport report;

    SandboxCheckResult(SandboxCheckReport report) {
        this.report = report;
    }

    public SandboxCheckReport getReport() {
        return report;
    }

}
