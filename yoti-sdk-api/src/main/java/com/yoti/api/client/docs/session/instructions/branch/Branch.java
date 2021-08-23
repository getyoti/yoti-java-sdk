package com.yoti.api.client.docs.session.instructions.branch;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Branch {

    @JsonProperty("type")
    private final String type;

    Branch(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
