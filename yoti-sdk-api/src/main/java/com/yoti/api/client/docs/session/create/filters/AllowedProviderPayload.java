package com.yoti.api.client.docs.session.create.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Identifies a digital ID provider (e.g. {@code DIGILOCKER}) that is allowed
 * to satisfy a {@link DocumentFilter}.
 */
public class AllowedProviderPayload {

    @JsonProperty("name")
    private final String name;

    private AllowedProviderPayload(String name) {
        this.name = name;
    }

    public static AllowedProviderPayload.Builder builder() {
        return new AllowedProviderPayload.Builder();
    }

    /**
     * The name of the allowed digital ID provider
     *
     * @return the provider name
     */
    public String getName() {
        return name;
    }

    public static class Builder {

        private String name;

        private Builder() {}

        /**
         * Sets the name of the digital ID provider
         *
         * @param name the provider name
         * @return the builder
         */
        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public AllowedProviderPayload build() {
            return new AllowedProviderPayload(name);
        }

    }

}
