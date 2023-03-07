package com.yoti.api.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Allows properties for Digital Identity configuration to be supplied via spring properties (e.g. YAML or properties file).
 */
@ConfigurationProperties(prefix = "com.yoti.identity")
public class DigitalIdentityProperties {

    /**
     * The Yoti Hub Application ID.
     */
    private String applicationId;

    /**
     * Gets the Yoti Hub Application ID.
     *
     * @return the Application ID.
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the Yoti Hub Application ID.
     *
     * @param id the new Application ID.
     */
    public void setApplicationId(String id) {
        applicationId = id;
    }

}
