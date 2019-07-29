package com.yoti.api.client.shareurl;

import java.util.List;
import com.yoti.api.client.shareurl.extension.Extension;
import com.yoti.api.client.shareurl.policy.DynamicPolicy;

/**
 * Data required when initiating a dynamic share
 *
 */
public interface DynamicScenario {

    /**
     * The device's callback endpoint. Must be a URL relative to the Application Domain specified in your Hub
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
