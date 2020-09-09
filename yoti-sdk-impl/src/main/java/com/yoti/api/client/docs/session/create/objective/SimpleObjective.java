package com.yoti.api.client.docs.session.create.objective;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract class SimpleObjective implements Objective {

    @JsonProperty("type")
    private final String type;

    SimpleObjective(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}
