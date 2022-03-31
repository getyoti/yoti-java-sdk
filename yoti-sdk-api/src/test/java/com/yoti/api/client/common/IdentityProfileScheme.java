package com.yoti.api.client.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class IdentityProfileScheme {

    @JsonProperty("type")
    private final String type;

    @JsonProperty("objective")
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
