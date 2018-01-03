package com.yoti.api.client.spi.remote.call.aml;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AmlAddress {

    @JsonProperty("post_code")
    private final String postCode;

    @JsonProperty("country")
    private final String country;

    public AmlAddress(String postCode, String country) {
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
        return "AmlAddress{" +
                "postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

}
