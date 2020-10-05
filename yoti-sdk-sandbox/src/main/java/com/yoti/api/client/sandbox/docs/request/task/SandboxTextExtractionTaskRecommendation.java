package com.yoti.api.client.sandbox.docs.request.task;

import com.yoti.api.client.sandbox.docs.SandboxDocScanConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SandboxTextExtractionTaskRecommendation {

    @JsonProperty("value")
    private final String value;

    @JsonProperty("reason")
    private final SandboxTextExtractionTaskReason reason;

    SandboxTextExtractionTaskRecommendation(String value, SandboxTextExtractionTaskReason reason) {
        this.value = value;
        this.reason = reason;
    }

    public String getValue() {
        return value;
    }

    public SandboxTextExtractionTaskReason getReason() {
        return reason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String recommendationValue;
        private SandboxTextExtractionTaskReason reason;

        public Builder forProgress() {
            this.recommendationValue = SandboxDocScanConstants.PROGRESS;
            return this;
        }

        public Builder forShouldTryAgain() {
            this.recommendationValue = SandboxDocScanConstants.SHOULD_TRY_AGAIN;
            return this;
        }

        public Builder forMustTryAgain() {
            this.recommendationValue = SandboxDocScanConstants.MUST_TRY_AGAIN;
            return this;
        }

        public Builder withReason(SandboxTextExtractionTaskReason reason) {
            this.reason = reason;
            return this;
        }

        public SandboxTextExtractionTaskRecommendation build() {
            return new SandboxTextExtractionTaskRecommendation(recommendationValue, reason);
        }

    }

}
