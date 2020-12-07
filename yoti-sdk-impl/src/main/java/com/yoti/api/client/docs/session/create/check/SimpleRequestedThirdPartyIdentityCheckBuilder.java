package com.yoti.api.client.docs.session.create.check;

public class SimpleRequestedThirdPartyIdentityCheckBuilder implements RequestedThirdPartyIdentityCheckBuilder {

    private final RequestedThirdPartyIdentityConfig config;

    SimpleRequestedThirdPartyIdentityCheckBuilder() {
        config = new SimpleRequestedThirdPartyIdentityConfig();
    }

    @Override
    public RequestedThirdPartyIdentityCheck<?> build() {
        return new SimpleRequestedThirdPartyIdentityCheck(config);
    }

}
