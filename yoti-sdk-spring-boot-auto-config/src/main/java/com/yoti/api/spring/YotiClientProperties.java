package com.yoti.api.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Allows properties to the Yoti client to be supplied via spring properties (e.g. YAML or properties file).
 */
@ConfigurationProperties(prefix = "com.yoti.client")
public class YotiClientProperties {

    /**
     * The SDK client id provided by Yoti dashboard.
     */
    private String clientSdkId;

    /**
     * The access security key to be used as the Spring resource loader format location to the private key provided by Yoti Dashboard.
     */
    private String accessSecurityKey;

    /**
     * Gets the Yoti client SDK id that is provided to the client developer via Yoti dashboard.
     *
     * @return the Yoti client SDK id.
     */
    public String getClientSdkId() {
        return clientSdkId;
    }

    /**
     * Sets the Yoti Yoti client SDK id that is provided to the client developer via Yoti portal.
     *
     * @param clientSdkId the Yoti client SDK id.
     */
    public void setClientSdkId(final String clientSdkId) {
        this.clientSdkId = clientSdkId;
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
    public void setAccessSecurityKey(final String accessSecurityKey) {
        this.accessSecurityKey = accessSecurityKey;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YotiClientProperties that = (YotiClientProperties) o;

        if (clientSdkId != null ? !clientSdkId.equals(that.clientSdkId) : that.clientSdkId != null) return false;
        return accessSecurityKey != null ? accessSecurityKey.equals(that.accessSecurityKey) : that.accessSecurityKey == null;

    }

    @Override
    public int hashCode() {
        int result = clientSdkId != null ? clientSdkId.hashCode() : 0;
        result = 31 * result + (accessSecurityKey != null ? accessSecurityKey.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "YotiClientProperties{" +
                "clientSdkId='" + clientSdkId + '\'' +
                ", accessSecurityKey='" + accessSecurityKey + '\'' +
                '}';
    }
}
