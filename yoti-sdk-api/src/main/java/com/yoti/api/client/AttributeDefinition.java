package com.yoti.api.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttributeDefinition {

    @JsonProperty("name")
    private final String name;

    public AttributeDefinition(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the definition.
     *
     * @return the name, or null if no name exists
     */
    public String getName() {
        return name;
    }

}
