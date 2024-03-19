package com.yoti.api.client.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class IdentityProfile {

    @JsonProperty(Share.Policy.Profile.TRUST_FRAMEWORK)
    private final String framework;

    @JsonProperty(Share.Policy.Profile.SCHEME)
    private final IdentityProfileScheme scheme;

    public IdentityProfile(String framework, IdentityProfileScheme scheme) {
        this.framework = framework;
        this.scheme = scheme;
    }

    public String getFramework() {
        return framework;
    }

    public IdentityProfileScheme getScheme() {
        return scheme;
    }

}
