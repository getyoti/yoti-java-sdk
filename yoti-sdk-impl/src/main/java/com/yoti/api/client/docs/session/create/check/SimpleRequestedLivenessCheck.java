package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedLivenessCheck
        extends SimpleRequestedCheck<RequestedLivenessConfig>
        implements RequestedLivenessCheck<RequestedLivenessConfig> {

    private final RequestedLivenessConfig config;

    SimpleRequestedLivenessCheck(RequestedLivenessConfig config) {
        this.config = config;
    }

    @Override
    public String getType() {
        return DocScanConstants.LIVENESS;
    }

    @Override
    public RequestedLivenessConfig getConfig() {
        return config;
    }

}
