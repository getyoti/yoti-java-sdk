package com.yoti.api.client.docs.session.retrieve.instructions.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationResponse {

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

}
