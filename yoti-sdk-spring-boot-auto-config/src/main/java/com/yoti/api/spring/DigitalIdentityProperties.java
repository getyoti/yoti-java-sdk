package com.yoti.api.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.yoti.identity")
public class DigitalIdentityProperties {

    private String applicationId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String id) {
        applicationId = id;
    }

}
