package com.yoti.api.client.docs.session.create;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IbvOptions {

    @JsonProperty("support")
    private final String support;

    @JsonProperty("guidance_url")
    private final String guidanceUrl;

    @JsonProperty("user_price")
    private final UserPrice userPrice;

    private IbvOptions(String support, String guidanceUrl, UserPrice userPrice) {
        this.support = support;
        this.guidanceUrl = guidanceUrl;
        this.userPrice = userPrice;
    }

    public static IbvOptions.Builder builder() {
        return new IbvOptions.Builder();
    }

    public String getSupport() {
        return support;
    }

    public String getGuidanceUrl() {
        return guidanceUrl;
    }

    public UserPrice getUserPrice() {
        return userPrice;
    }

    public static class Builder {

        private String support;
        private String guidanceUrl;
        private UserPrice userPrice;

        private Builder() {}

        /**
         * Sets the support type = NOT_ALLOWED
         *
         * @return the builder
         */
        public Builder withIbvNotAllowed() {
            return withSupport(DocScanConstants.NOT_ALLOWED);
        }

        /**
         * Sets the support type = MANDATORY
         *
         * @return the builder
         */
        public Builder withIbvMandatory() {
            return withSupport(DocScanConstants.MANDATORY);
        }

        /**
         * Sets the support type
         *
         * @param support the support type
         * @return the builder
         */
        public Builder withSupport(String support) {
            this.support = support;
            return this;
        }

        /**
         * Sets the guidance url
         *
         * @param guidanceUrl the guidance url
         * @return the builder
         */
        public Builder withGuidanceUrl(String guidanceUrl) {
            this.guidanceUrl = guidanceUrl;
            return this;
        }

        /**
         * Sets the {@link UserPrice}
         *
         * @param userPrice the {@link UserPrice}
         * @return the builder
         */
        public Builder withUserPrice(UserPrice userPrice) {
            this.userPrice = userPrice;
            return this;
        }

        public IbvOptions build() {
            return new IbvOptions(support, guidanceUrl, userPrice);
        }

    }

}
