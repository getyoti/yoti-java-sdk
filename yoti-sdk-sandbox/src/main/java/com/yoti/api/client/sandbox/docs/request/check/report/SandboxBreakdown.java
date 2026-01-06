package com.yoti.api.client.sandbox.docs.request.check.report;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxBreakdown {

    @JsonProperty("sub_check")
    private final String subCheck;

    @JsonProperty("result")
    private final String result;

    @JsonProperty("details")
    private final List<SandboxDetail> details;

    private SandboxBreakdown(String subCheck, String result, List<SandboxDetail> details) {
        this.subCheck = subCheck;
        this.result = result;
        this.details = details;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSubCheck() {
        return subCheck;
    }

    public String getResult() {
        return result;
    }

    public List<SandboxDetail> getDetails() {
        return details;
    }

    /**
     * Builder for {@link SandboxBreakdown}
     */
    public static class Builder {

        private String subCheck;
        private String result;
        private List<SandboxDetail> details = new ArrayList<>();

        private Builder() {
        }

        public Builder withSubCheck(String subCheck) {
            this.subCheck = subCheck;
            return this;
        }

        public Builder withResult(String result) {
            this.result = result;
            return this;
        }

        public Builder withDetail(String name, String value) {
            SandboxDetail detail = new SandboxDetail(name, value);
            details.add(detail);
            return this;
        }

        public SandboxBreakdown build() {
            notNullOrEmpty(subCheck, "subCheck");
            notNullOrEmpty(result, "result");

            return new SandboxBreakdown(subCheck, result, details);
        }
    }
}
