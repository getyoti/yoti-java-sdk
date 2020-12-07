package com.yoti.api.client.docs.session.create.check;

public interface RequestedThirdPartyIdentityCheck<T extends RequestedThirdPartyIdentityConfig> extends RequestedCheck<T> {

    @Override
    T getConfig();

}
