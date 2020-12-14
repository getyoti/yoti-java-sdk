package com.yoti.api.client.spi.remote.call.aml;

import com.yoti.api.client.aml.AmlAddress;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleAmlAddress implements AmlAddress {

    @JsonProperty("post_code")
    private final String postCode;

    @JsonProperty("country")
    private final String country;

    public SimpleAmlAddress(String postCode, String country) {
        this.postCode = postCode;
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return country;
    }

}
