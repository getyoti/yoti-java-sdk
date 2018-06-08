package com.yoti.api.client.qrcode;

import java.util.List;
import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.Policy;

/**
 * Data required for the {@link DynamicQRCode} generation
 *
 */
public interface DynamicScenario {

    /**
     * The device's callback endpoint
     *
     */
    String callbackEndpoint();

    /**
     * The customisable {@link Policy} for the {@link DynamicQRCode}
     *
     */
    Policy policy();

    /**
     * Set of {@link Exception} to be activated for the application
     *
     */
    List<Extension> extensions();

}
