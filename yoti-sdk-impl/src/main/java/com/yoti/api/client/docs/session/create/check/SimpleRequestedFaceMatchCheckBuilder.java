package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleRequestedFaceMatchCheckBuilder implements RequestedFaceMatchCheckBuilder {

    private String manualCheck;

    @Override
    public SimpleRequestedFaceMatchCheckBuilder withManualCheckAlways() {
        this.manualCheck = DocScanConstants.ALWAYS;
        return this;
    }

    @Override
    public SimpleRequestedFaceMatchCheckBuilder withManualCheckFallback() {
        this.manualCheck = DocScanConstants.FALLBACK;
        return this;
    }

    @Override
    public SimpleRequestedFaceMatchCheckBuilder withManualCheckNever() {
        this.manualCheck = DocScanConstants.NEVER;
        return this;
    }

    @Override
    public RequestedFaceMatchCheck build() {
        notNullOrEmpty(manualCheck, "manualCheck");

        RequestedFaceMatchConfig config = new SimpleRequestedFaceMatchConfig(manualCheck);
        return new SimpleRequestedFaceMatchCheck(config);
    }

}
