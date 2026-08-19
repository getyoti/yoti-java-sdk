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

    @JsonProperty("handled_check_limit")
    private final Integer handledCheckLimit;

    SandboxCheck(SandboxCheckResult result, Integer handledCheckLimit) {
        this.result = result;
        this.handledCheckLimit = handledCheckLimit;
    }

    public SandboxCheckResult getResult() {
        return result;
    }

    public Integer getHandledCheckLimit() {
        return handledCheckLimit;
    }

    static abstract class Builder<T extends Builder<T>> {

        protected SandboxRecommendation recommendation;
        protected List<SandboxBreakdown> breakdown;
        protected String reportTemplate;
        protected Integer handledCheckLimit;

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

        public T withBreakdowns(List<SandboxBreakdown> breakdowns) {
            this.breakdown = breakdowns;
            return self();
        }

        public T withReportTemplate(String reportTemplate) {
            this.reportTemplate = reportTemplate;
            return self();
        }

        public T withHandledCheckLimit(Integer handledCheckLimit) {
            this.handledCheckLimit = handledCheckLimit;
            return self();
        }

        protected abstract T self();

        public abstract SandboxCheck build();
    }

}
