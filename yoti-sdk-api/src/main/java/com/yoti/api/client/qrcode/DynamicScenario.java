package com.yoti.api.client.qrcode;

import java.util.List;
import com.yoti.api.client.qrcode.extension.Extension;
import com.yoti.api.client.qrcode.policy.Policy;

/**
 * DynamicScenario
 */
public interface DynamicScenario {

    /**
     * callbackEndpoint
     */
    String callbackEndpoint();

    /**
     * policy
     */
    Policy policy();

    /**
     * extensions
     */
    List<Extension> extensions();

}
