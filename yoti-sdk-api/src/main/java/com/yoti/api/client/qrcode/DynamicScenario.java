package com.yoti.api.client.qrcode;

import java.util.List;
import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.DynamicPolicy;

/**
 * Data required when initiating a dynamic share
 *
 */
public interface DynamicScenario {

    /**
     * The device's callback endpoint.  Must be a URL relative to the Application Domain specified in your Dashboard
     *
     */
    String callbackEndpoint();

    /**
     * The customisable {@link DynamicPolicy} to use in the share
     *
     */
    DynamicPolicy policy();

    /**
     * List of {@link Extension} to be activated for the application
     *
     */
    List<Extension> extensions();

}
