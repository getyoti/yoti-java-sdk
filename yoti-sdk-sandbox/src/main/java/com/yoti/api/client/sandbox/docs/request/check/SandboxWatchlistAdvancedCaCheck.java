package com.yoti.api.client.sandbox.docs.request.check;

import java.util.List;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxCheckReport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxWatchlistAdvancedCaCheck extends SandboxCheck {

    @JsonProperty("sources_filter")
    private final SandboxCaSourcesFilter sourcesFilter;

    SandboxWatchlistAdvancedCaCheck(SandboxCheckResult result, SandboxCaSourcesFilter sourcesFilter) {
        super(result);
        this.sourcesFilter = sourcesFilter;
    }

    public SandboxCaSourcesFilter getSourcesFilter() {
        return sourcesFilter;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link SandboxWatchlistAdvancedCaCheck}
     */
    public static class Builder extends SandboxCheck.Builder<Builder> {

        private SandboxCaSourcesFilter sourcesFilter;

        private Builder() {}

        public Builder withTypeListFilter(List<String> types) {
            this.sourcesFilter = new SandboxCaTypeListSourcesFilter(types);
            return self();
        }

        public Builder withProfileFilter(String searchProfile) {
            this.sourcesFilter = new SandboxCaProfileSourcesFilter(searchProfile);
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public SandboxWatchlistAdvancedCaCheck build() {
            SandboxCheckReport report = recommendation == null && breakdown == null
                    ? null
                    : new SandboxCheckReport(recommendation, breakdown);
            SandboxCheckResult result = new SandboxCheckResult(report, reportTemplate);

            return new SandboxWatchlistAdvancedCaCheck(result, sourcesFilter);
        }

    }
}
