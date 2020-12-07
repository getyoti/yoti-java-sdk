package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedThirdPartyIdentityCheck
        extends SimpleRequestedCheck<RequestedThirdPartyIdentityConfig>
        implements RequestedThirdPartyIdentityCheck<RequestedThirdPartyIdentityConfig> {

    private final RequestedThirdPartyIdentityConfig config;

    SimpleRequestedThirdPartyIdentityCheck(RequestedThirdPartyIdentityConfig config) {
        this.config = config;
    }

    @Override
    public String getType() {
        return DocScanConstants.THIRD_PARTY_IDENTITY;
    }

    @Override
    public RequestedThirdPartyIdentityConfig getConfig() {
        return config;
    }

}
