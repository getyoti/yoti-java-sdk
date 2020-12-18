package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailsResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
