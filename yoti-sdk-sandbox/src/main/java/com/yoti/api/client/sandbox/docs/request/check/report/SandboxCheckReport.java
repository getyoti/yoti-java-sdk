package com.yoti.api.client.sandbox.docs.request.check.report;

import java.util.List;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxBreakdown;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxRecommendation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SandboxCheckReport {

    @JsonProperty("recommendation")
    private SandboxRecommendation recommendation;

    @JsonProperty("breakdown")
    private List<SandboxBreakdown> breakdown;

    public SandboxCheckReport(SandboxRecommendation recommendation, List<SandboxBreakdown> breakdown) {
        this.recommendation = recommendation;
        this.breakdown = breakdown;
    }

    public SandboxRecommendation getRecommendation() {
        return recommendation;
    }

    public List<SandboxBreakdown> getBreakdown() {
        return breakdown;
    }
}
