package com.yoti.api.client;

/**
 * Configuration necessary to create a YotiClient instance.
 *
 */
public interface YotiClientConfiguration {
    String getApplicationId();

    KeyPairSource getKeyPairSource();
}