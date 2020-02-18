package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleGeneratedMedia implements GeneratedMedia {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }
}
