package com.yoti.api.client.docs;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.KeyPairSource;

public class SimpleDocScanClientBuilder extends DocScanClientBuilder {

    private static final DocScanService docScanService = DocScanService.newInstance();

    private String sdkId;
    private KeyPairSource keyPairSource;

    @Override
    public DocScanClientBuilder withSdkId(String sdkId) {
        this.sdkId = sdkId;
        return this;
    }

    @Override
    public DocScanClientBuilder withKeyPairSource(KeyPairSource kps) {
        this.keyPairSource = kps;
        return this;
    }

    @Override
    public DocScanClient build() {
        notNullOrEmpty(sdkId, "SDK ID");
        notNull(keyPairSource, "Application key Pair");

        return new SimpleDocScanClient(
                sdkId,
                keyPairSource,
                docScanService
        );
    }

}
