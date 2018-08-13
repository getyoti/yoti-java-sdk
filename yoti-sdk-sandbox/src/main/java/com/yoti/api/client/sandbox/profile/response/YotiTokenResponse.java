package com.yoti.api.client.sandbox.profile.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YotiTokenResponse {

    private String token;

    @JsonCreator
    public YotiTokenResponse(@JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
