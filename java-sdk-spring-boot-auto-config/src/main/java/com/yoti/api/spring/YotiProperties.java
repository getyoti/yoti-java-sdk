package com.yoti.api.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Allows properties for Yoti configuration to be supplied via spring properties (e.g. YAML or properties file).
 */
@ConfigurationProperties(prefix = "com.yoti")
public class YotiProperties {

    /**
     * The Yoti Dashboard Application ID.
     */
    private String applicationId;

    /**
     * The Yoti Dashboard Scenario ID.
     */
    private String scenarioId;

    /**
     * Gets the Yoti Dashboard Application ID.
     *
     * @return the Application ID.
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the Yoti Dashboard Application ID.
     *
     * @param applicationId the new Application ID.
     */
    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Gets the Yoti Dashboard Scenario ID.
     *
     * @return the scenario ID.
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Sets the scenario ID given to you by Yoti Dashboard.
     *
     * @param scenarioId the scenario ID.
     */
    public void setScenarioId(final String scenarioId) {
        this.scenarioId = scenarioId;
    }

    @Override
    public String toString() {
        return "YotiProperties{" +
                "applicationId='" + applicationId + '\'' +
                ", scenarioId='" + scenarioId + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final YotiProperties that = (YotiProperties) o;

        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        return scenarioId != null ? scenarioId.equals(that.scenarioId) : that.scenarioId == null;
    }

    @Override
    public int hashCode() {
        int result = applicationId != null ? applicationId.hashCode() : 0;
        result = 31 * result + (scenarioId != null ? scenarioId.hashCode() : 0);
        return result;
    }
}
