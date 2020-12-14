package com.yoti.api.client.aml;

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

    public static AmlAddress.Builder builder() {
        return new AmlAddress.Builder();
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return country;
    }

    public static class Builder {

        private String postCode;
        private String country;

        public Builder withPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public AmlAddress build() {
            return new AmlAddress(postCode, country);
        }

    }

}
