package com.yoti.api.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Allows properties to the Yoti client to be supplied via spring properties (e.g. YAML or properties file).
 */
@ConfigurationProperties(prefix = "com.yoti.client")
public class YotiClientProperties {

    /**
     * The SDK client ID provided by Yoti Hub.
     */
    private String clientSdkId;

    /**
     * The scenario ID provided by Yoti dashboard
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
    public void setClientSdkId(final String clientSdkId) {
        this.clientSdkId = clientSdkId;
    }

    /**
     * Gets the Yoti scenario ID that is provided to the client developer via Yoti Hub.
     *
     * @return the Yoti scenario ID.
     */
    public String getScenarioId() { return scenarioId; };

    /**
     * Sets the Yoti scenario ID that is provided to the client developer via Yoti Hub.
     *
     * @param scenarioId the Yoti scenario ID.
     */
    public void setScenarioId(final String scenarioId) { this.scenarioId = scenarioId; }

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
    public void setAccessSecurityKey(final String accessSecurityKey) {
        this.accessSecurityKey = accessSecurityKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YotiClientProperties that = (YotiClientProperties) o;
        if (scenarioId != null ? !scenarioId.equals(that.scenarioId) : that.scenarioId != null) return false;
        if (clientSdkId != null ? !clientSdkId.equals(that.clientSdkId) : that.clientSdkId != null) return false;

        return accessSecurityKey != null ? accessSecurityKey.equals(that.accessSecurityKey) : that.accessSecurityKey == null;
    }

    @Override
    public int hashCode() {
        int result = scenarioId != null ? scenarioId.hashCode() : 0;
        result = 31 * result + (clientSdkId != null ? clientSdkId.hashCode() : 0);
        result = 31 * result + (accessSecurityKey != null ? accessSecurityKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("YotiClientProperties{");
        sb.append("scenarioId='").append(scenarioId).append('\'');
        sb.append(", clientSdkId='").append(clientSdkId).append('\'');
        sb.append(", accessSecurityKey='").append(accessSecurityKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
