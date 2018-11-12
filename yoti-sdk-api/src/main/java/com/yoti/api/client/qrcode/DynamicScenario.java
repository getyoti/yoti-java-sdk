package com.yoti.api.client.qrcode;

import java.util.List;
import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

/**
 * Data required for the {@link QrCode} generation
 *
 */
public interface DynamicScenario {

    /**
     * The device's callback endpoint
     *
     */
    String callbackEndpoint();

    /**
     * The customisable {@link DynamicPolicy} for the {@link QrCode}
     *
     */
    DynamicPolicy policy();

    /**
     * List of {@link Extension} to be activated for the application
     *
     */
    List<Extension> extensions();

}
