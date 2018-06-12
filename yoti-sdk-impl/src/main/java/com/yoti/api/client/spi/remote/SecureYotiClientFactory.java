package com.yoti.api.client.spi.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.YotiClient;
import com.yoti.api.client.YotiClientConfiguration;
import com.yoti.api.client.YotiClientFactory;
import com.yoti.api.client.spi.remote.call.RemoteProfileService;
import com.yoti.api.client.spi.remote.call.aml.RemoteAmlService;

public final class SecureYotiClientFactory implements YotiClientFactory {
    private static final Logger LOG = LoggerFactory.getLogger(SecureYotiClientFactory.class);

    @Override
    public boolean accepts(String applicationId) {
        return true;
    }

    @Override
    public YotiClient getInstance(YotiClientConfiguration configuration) throws InitialisationException {
        LOG.debug("Creating SecureYotiClient for application {} from {}", configuration.getApplicationId(),
                configuration.getKeyPairSource());
        return new SecureYotiClient(configuration.getApplicationId(),
                configuration.getKeyPairSource(),
                RemoteProfileService.newInstance(),
                RemoteAmlService.newInstance(),
                new AttributeConverter());
    }

}
