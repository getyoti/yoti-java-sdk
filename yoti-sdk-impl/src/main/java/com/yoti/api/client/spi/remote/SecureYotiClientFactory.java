package com.yoti.api.client.spi.remote;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.client.YotiClientConfiguration;
import com.yoti.api.client.YotiClientFactory;
import com.yoti.api.client.spi.remote.call.aml.RemoteAmlService;
import com.yoti.api.client.spi.remote.call.qrcode.RemoteQrCodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SecureYotiClientFactory implements YotiClientFactory {

    private static final Logger LOG = LoggerFactory.getLogger(SecureYotiClientFactory.class);

    @Override
    public boolean accepts(String applicationId) {
        return true;
    }

    @Override
    public YotiClient getInstance(YotiClientConfiguration configuration) throws InitialisationException {
        LOG.debug("Creating SecureYotiClient for application '{}' from '{}'", configuration.getApplicationId(), configuration.getKeyPairSource());
        return new SecureYotiClient(configuration.getApplicationId(),
                configuration.getKeyPairSource(),
                ReceiptFetcher.newInstance(),
                ActivityDetailsFactory.newInstance(),
                RemoteAmlService.newInstance(),
                RemoteQrCodeService.newInstance()
        );
    }

}
