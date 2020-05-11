package com.yoti.docscan.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConditionalOnProperty(value = "com.yoti.docscan")
@ConfigurationProperties(prefix = "com.yoti.docscan")
public class DocScanConfig {

    private String sdkId;

    private String pemFileLocation;

    public String getSdkId() {
        return sdkId;
    }

    public void setSdkId(String sdkId) {
        this.sdkId = sdkId;
    }

    public String getPemFileLocation() {
        return pemFileLocation;
    }

    public void setPemFileLocation(String pemFileLocation) {
        this.pemFileLocation = pemFileLocation;
    }
}
