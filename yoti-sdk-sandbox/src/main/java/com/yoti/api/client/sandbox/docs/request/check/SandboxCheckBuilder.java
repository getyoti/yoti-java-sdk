package com.yoti.api.client.sandbox.docs.request.check;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxBreakdown;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxRecommendation;

abstract class SandboxCheckBuilder<T extends SandboxCheckBuilder<T>> {

    protected SandboxRecommendation recommendation;
    protected List<SandboxBreakdown> breakdown;

    public T withRecommendation(SandboxRecommendation recommendation) {
        this.recommendation = recommendation;
        return self();
    }

    public T withBreakdown(SandboxBreakdown breakdown) {
        if (this.breakdown == null) {
            this.breakdown = new ArrayList<>();
        }

        this.breakdown.add(breakdown);
        return self();
    }

    public T withBreakdownList(List<SandboxBreakdown> breakdownList) {
        this.breakdown = breakdownList;
        return self();
    }

    protected abstract T self();

    public abstract SandboxCheck build();

}
