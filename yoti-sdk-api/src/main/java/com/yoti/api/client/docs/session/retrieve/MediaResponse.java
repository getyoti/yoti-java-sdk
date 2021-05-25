package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MediaResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("created")
    private String created;

    @JsonProperty("last_updated")
    private String lastUpdated;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCreated() {
        return created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

}
