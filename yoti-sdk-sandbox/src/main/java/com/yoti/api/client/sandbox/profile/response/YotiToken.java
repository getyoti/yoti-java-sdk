package com.yoti.api.client.sandbox.profile.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YotiToken {

    private String token;

    @JsonCreator
    public YotiToken(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
