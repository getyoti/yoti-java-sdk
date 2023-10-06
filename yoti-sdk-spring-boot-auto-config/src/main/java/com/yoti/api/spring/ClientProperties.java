package com.yoti.api.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Allows properties to the clients to be supplied via spring properties (e.g. YAML or properties file).
 */
@ConfigurationProperties(prefix = "com.yoti.client")
public class ClientProperties {

    /**
     * The SDK client ID provided by Yoti Hub.
     */
    private String clientSdkId;

    /**
     * The scenario ID provided by Yoti Hub
     */
    private String scenarioId;

    /**
     * The access security key to be used as the Spring resource loader format location to the private key provided by Yoti Hub.
     */
    private String accessSecurityKey;

    /**
     * Gets the Yoti client SDK ID that is provided to the client developer via Yoti Hub.
     *
     * @return the Yoti client SDK ID.
     */
    public String getClientSdkId() {
        return clientSdkId;
    }

    /**
     * Sets the Yoti client SDK ID that is provided to the client developer via Yoti portal.
     *
     * @param clientSdkId the Yoti client SDK ID.
     */
    public void setClientSdkId(String clientSdkId) {
        this.clientSdkId = clientSdkId;
    }

    /**
     * Gets the Yoti scenario ID that is provided to the client developer via Yoti Hub.
     *
     * @return the Yoti scenario ID.
     */
    public String getScenarioId() {
        return scenarioId;
    }

    /**
     * Sets the Yoti scenario ID that is provided to the client developer via Yoti Hub.
     *
     * @param scenarioId the Yoti scenario ID.
     */
    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    /**
     * Gets the location for the key pair.
     *
     * @return the key pair location string.
     */
    public String getAccessSecurityKey() {
        return accessSecurityKey;
    }

    /**
     * Sets the location for the key pair. This is expected to be formatted as a Spring resource string (a classpath, URL or file resource).
     *
     * @param accessSecurityKey the key pair location in spring resource format e.g. classpath: or file: or as a URL.
     * @see org.springframework.core.io.Resource
     */
    public void setAccessSecurityKey(String accessSecurityKey) {
        this.accessSecurityKey = accessSecurityKey;
    }

}
