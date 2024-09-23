package com.yoti.api.client.sandbox.docs.request.task;

import com.yoti.api.client.sandbox.docs.SandboxDocScanConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SandboxTextExtractionTaskReason {

    @JsonProperty("value")
    private final String value;

    @JsonProperty("detail")
    private final String detail;

    SandboxTextExtractionTaskReason(String value, String detail) {
        this.value = value;
        this.detail = detail;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getValue() {
        return value;
    }

    public String getDetail() {
        return detail;
    }

    public static class Builder {

        private String value;
        private String detail;

        public Builder forQuality() {
            value = SandboxDocScanConstants.QUALITY;
            return this;
        }

        public Builder forUserError() {
            value = SandboxDocScanConstants.USER_ERROR;
            return this;
        }

        public Builder withDetail(String detail) {
            this.detail = detail;
            return this;
        }

        public SandboxTextExtractionTaskReason build() {
            return new SandboxTextExtractionTaskReason(value, detail);
        }

    }

}
