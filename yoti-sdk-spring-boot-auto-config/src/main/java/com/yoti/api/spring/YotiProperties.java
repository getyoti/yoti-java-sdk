package com.yoti.api.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Allows properties for Yoti configuration to be supplied via spring properties (e.g. YAML or properties file).
 */
@ConfigurationProperties(prefix = "com.yoti")
public class YotiProperties {

    /**
     * The Yoti Hub Application ID.
     */
    private String applicationId;

    /**
     * The Yoti Hub Scenario ID.
     */
    private String scenarioId;

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

    /**
     * Gets the Yoti Hub Scenario ID.
     *
     * @return the scenario ID.
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Sets the scenario ID given to you by Yoti Hub.
     *
     * @param id the scenario ID.
     */
    public void setScenarioId(String id) {
        scenarioId = id;
    }

}
