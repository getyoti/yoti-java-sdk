package com.yoti.api.client;

public interface YotiClientFactory {
    boolean accepts(String applicationId);

    YotiClient getInstance(YotiClientConfiguration yotiClientConfiguration) throws InitialisationException;
}
