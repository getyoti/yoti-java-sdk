package com.yoti.api.client.identity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareSession {

    @JsonProperty(Property.ID)
    private String id;

    @JsonProperty(Property.STATUS)
    private String status;

    @JsonProperty(Property.EXPIRY)
    private String expiry;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getExpiry() {
        return expiry;
    }

    private static final class Property {

        private static final String ID = "id";
        private static final String STATUS = "status";
        private static final String EXPIRY = "expiry";

    }

}
