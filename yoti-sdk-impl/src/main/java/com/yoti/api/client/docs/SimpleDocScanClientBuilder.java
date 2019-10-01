package com.yoti.api.client.docs;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import com.yoti.api.client.KeyPairSource;

public class SimpleDocScanClientBuilder extends DocScanClientBuilder {

    private static final DocScanService docScanService = DocScanService.newInstance();

    private String appId;
    private KeyPairSource keyPairSource;

    @Override
    public DocScanClientBuilder withApplicationId(String appId) {
        this.appId = appId;
        return this;
    }

    @Override
    public DocScanClientBuilder withKeyPairSource(KeyPairSource kps) {
        this.keyPairSource = kps;
        return this;
    }

    @Override
    public DocScanClient build() {
        notNullOrEmpty(appId, "Application id");
        notNull(keyPairSource, "Application key Pair");

        return new SimpleDocScanClient(
                appId,
                keyPairSource,
                docScanService
        );
    }

}
