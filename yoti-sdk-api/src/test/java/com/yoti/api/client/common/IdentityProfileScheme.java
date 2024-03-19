package com.yoti.api.client.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class IdentityProfileScheme {

    @JsonProperty(Share.Policy.Profile.TYPE)
    private final String type;

    @JsonProperty(Share.Policy.Profile.OBJECTIVE)
    private final String objective;

    public IdentityProfileScheme(String type, String objective) {
        this.type = type;
        this.objective = objective;
    }

    public String getType() {
        return type;
    }

    public String getObjective() {
        return objective;
    }

}
