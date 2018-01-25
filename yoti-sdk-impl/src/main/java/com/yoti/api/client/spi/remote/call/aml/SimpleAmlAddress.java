package com.yoti.api.client.spi.remote.call.aml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.aml.AmlAddress;

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

    @Override
    public String toString() {
        return "SimpleAmlAddress{" +
                "postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

}
