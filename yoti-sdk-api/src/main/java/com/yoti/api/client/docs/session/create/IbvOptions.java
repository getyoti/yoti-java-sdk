package com.yoti.api.client.docs.session.create;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.spi.remote.util.Validation;

public class IbvOptions {

    private final String support;
    private final String guidanceUrl;

    private IbvOptions(String support, String guidanceUrl) {
        this.support = support;
        this.guidanceUrl = guidanceUrl;
    }

    public static IbvOptions.Builder builder() {
        return new IbvOptions.Builder();
    }

    public String getSupport() {
        return support;
    }

    public String getGuidanceUrl() {
        return guidanceUrl;
    }

    public static class Builder {

        private String support;
        private String guidanceUrl;

        private Builder() {}

        public Builder withIbvNotAllowed() {
            return withSupport(DocScanConstants.NOT_ALLOWED);
        }

        public Builder withIbvMandatory() {
            return withSupport(DocScanConstants.MANDATORY);
        }

        public Builder withSupport(String support) {
            this.support = support;
            return this;
        }

        public Builder withGuidanceUrl(String guidanceUrl) {
            this.guidanceUrl = guidanceUrl;
            return this;
        }

        public IbvOptions build() {
            return new IbvOptions(support, guidanceUrl);
        }

    }

}
