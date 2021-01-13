package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class RequestedThirdPartyIdentityCheck extends RequestedCheck<RequestedThirdPartyIdentityConfig> {

    private final RequestedThirdPartyIdentityConfig config;

    RequestedThirdPartyIdentityCheck(RequestedThirdPartyIdentityConfig config) {
        this.config = config;
    }

    public static RequestedThirdPartyIdentityCheck.Builder builder() {
        return new RequestedThirdPartyIdentityCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.THIRD_PARTY_IDENTITY;
    }

    @Override
    public RequestedThirdPartyIdentityConfig getConfig() {
        return config;
    }

    public static class Builder {

        private final RequestedThirdPartyIdentityConfig config;

        private Builder() {
            config = new RequestedThirdPartyIdentityConfig();
        }

        public RequestedThirdPartyIdentityCheck build() {
            return new RequestedThirdPartyIdentityCheck(config);
        }

    }

}
