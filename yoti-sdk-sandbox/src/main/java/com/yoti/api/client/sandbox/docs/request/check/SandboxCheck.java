package com.yoti.api.client.sandbox.docs.request.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxBreakdown;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxRecommendation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class SandboxCheck {

    @JsonProperty("result")
    private final SandboxCheckResult result;

    SandboxCheck(SandboxCheckResult result) {
        this.result = result;
    }

    public SandboxCheckResult getResult() {
        return result;
    }

    static abstract class Builder<T extends Builder<T>> {

        protected SandboxRecommendation recommendation;
        protected List<SandboxBreakdown> breakdown = new ArrayList<>();

        public T withRecommendation(SandboxRecommendation recommendation) {
            this.recommendation = recommendation;
            return self();
        }

        public T withBreakdown(SandboxBreakdown breakdown) {
            this.breakdown.add(breakdown);
            return self();
        }

        public T withBreakdowns(List<SandboxBreakdown> breakdowns) {
            notNull(breakdowns, "breakdowns");
            this.breakdown = breakdowns;
            return self();
        }

        protected abstract T self();

        public abstract SandboxCheck build();
    }

}
