package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequiredShareCodePayload {

    @JsonProperty("issuer")
    private final String issuer;

    @JsonProperty("scheme")
    private final String scheme;

    private RequiredShareCodePayload(String issuer, String scheme) {
        this.issuer = issuer;
        this.scheme = scheme;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getScheme() {
        return scheme;
    }

    public static RequiredShareCodePayload.Builder builder() {
        return new RequiredShareCodePayload.Builder();
    }

    public static final class Builder {

        private String issuer;
        private String scheme;

        /**
         * Sets the issuer of the required Share Code
         *
         * @param issuer the issuer
         * @return the builder
         */
        public Builder withIssuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        /**
         * Sets the scheme of the required Share Code
         *
         * @param scheme the scheme
         * @return
         */
        public Builder withScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        /**
         * Builds an {@link RequiredShareCodePayload} using the supplied properties in the builder.
         *
         * @return the built {@link RequiredShareCodePayload}
         */
        public RequiredShareCodePayload build() {
            return new RequiredShareCodePayload(issuer, scheme);
        }

    }

}
