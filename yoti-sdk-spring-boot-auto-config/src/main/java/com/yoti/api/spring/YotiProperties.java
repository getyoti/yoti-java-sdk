package com.yoti.api.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.yoti")
public class YotiProperties {

    private String applicationId;

    private String scenarioId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String id) {
        applicationId = id;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String id) {
        scenarioId = id;
    }

}
