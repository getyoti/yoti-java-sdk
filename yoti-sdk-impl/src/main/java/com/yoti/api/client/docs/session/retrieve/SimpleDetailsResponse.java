package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleDetailsResponse implements DetailsResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

}
