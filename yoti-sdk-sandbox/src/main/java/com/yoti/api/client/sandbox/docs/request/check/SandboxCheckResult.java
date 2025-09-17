package com.yoti.api.client.sandbox.docs.request.check;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

import com.fasterxml.jackson.annotation.JsonProperty;

class SandboxCheckResult {

    @JsonProperty("report")
    private final SandboxCheckReport report;

    @JsonProperty("report_template")
    private final String reportTemplate;

    SandboxCheckResult(SandboxCheckReport report, String reportTemplate) {
        this.report = report;
        this.reportTemplate = reportTemplate;
    }

    public SandboxCheckReport getReport() {
        return report;
    }

    public String getReportTemplate() {
        return reportTemplate;
    }
    
}
