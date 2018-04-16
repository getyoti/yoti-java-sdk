package com.yoti.api.client.sandbox.profile.request;

import java.util.List;

import com.yoti.api.client.sandbox.profile.request.attribute.Attribute;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class YotiTokenRequest {
    @JsonProperty("remember_me_id")
    private String rememberMeId;
    @JsonProperty("profile_attributes")
    private List<Attribute> attributes;

    @JsonCreator
    YotiTokenRequest(@JsonProperty("remember_me_id") String rememberMeId,
            @JsonProperty("profile_attributes") List<Attribute> attributes) {
        this.rememberMeId = rememberMeId;
        this.attributes = attributes;
    }

    public String getRememberMeId() {
        return rememberMeId;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

}
