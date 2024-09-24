package com.yoti.api.client.docs.session.devicemetadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataResponse {

    @JsonProperty("event")
    private String event;

    @JsonProperty("created")
    private String created;

    @JsonProperty("device")
    private DeviceResponse device;

    public String getEvent() {
        return event;
    }

    public String getCreated() {
        return created;
    }

    public DeviceResponse getDevice() {
        return device;
    }

}
