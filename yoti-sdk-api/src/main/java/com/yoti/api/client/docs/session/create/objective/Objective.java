package com.yoti.api.client.docs.session.create.objective;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Objective {

    @JsonProperty("type")
    private final String type;

    Objective(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
