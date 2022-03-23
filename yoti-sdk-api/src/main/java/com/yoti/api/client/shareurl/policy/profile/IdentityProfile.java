package com.yoti.api.client.shareurl.policy.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Set of data required to represent an identity profile
 */
public final class IdentityProfile {

    @JsonProperty(Property.TRUST_FRAMEWORK)
    private final String framework;

    @JsonProperty(Property.SCHEME)
    private final IdentityProfileScheme scheme;

    IdentityProfile(String framework, IdentityProfileScheme scheme) {
        this.framework = framework;
        this.scheme = scheme;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Defines under which trust framework this identity profile should be verified.
     *
     * @return framework
     */
    public String getFramework() {
        return framework;
    }

    /**
     * Defines which scheme this identity profile should satisfy.
     *
     * @return scheme
     */
    public IdentityProfileScheme getScheme() {
        return scheme;
    }

    public static final class Builder {

        private String framework;
        private IdentityProfileScheme scheme;

        private Builder() { }

        public Builder withFramework(String framework) {
            this.framework = framework;

            return this;
        }

        public Builder withScheme(IdentityProfileScheme scheme) {
            this.scheme = scheme;
            return this;
        }

        public IdentityProfile build() {
            return new IdentityProfile(framework, scheme);
        }

    }

    private static final class Property {

        private Property() { }

        private static final String TRUST_FRAMEWORK = "trust_framework";
        private static final String SCHEME = "scheme";

    }

}
