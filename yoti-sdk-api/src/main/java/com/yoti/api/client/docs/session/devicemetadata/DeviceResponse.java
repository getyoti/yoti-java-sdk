package com.yoti.api.client.docs.session.devicemetadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceResponse {

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("ip_iso_country_code")
    private String ipIsoCountryCode;

    @JsonProperty("manufacture_name")
    private String manufactureName;

    @JsonProperty("model_name")
    private String modelName;

    @JsonProperty("os_name")
    private String osName;

    @JsonProperty("os_version")
    private String osVersion;

    @JsonProperty("browser_name")
    private String browserName;

    @JsonProperty("browser_version")
    private String browserVersion;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("client_version")
    private String clientVersion;

    public String getIpAddress() {
        return ipAddress;
    }

    public String getIpIsoCountryCode() {
        return ipIsoCountryCode;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getOsName() {
        return osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getBrowserName() {
        return browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public String getLocale() {
        return locale;
    }

    public String getClientVersion() {
        return clientVersion;
    }

}
