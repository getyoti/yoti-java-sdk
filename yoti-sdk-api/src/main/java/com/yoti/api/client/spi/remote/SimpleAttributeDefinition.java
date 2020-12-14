package com.yoti.api.client.spi.remote;

import com.yoti.api.client.AttributeDefinition;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleAttributeDefinition implements AttributeDefinition {

    @JsonProperty("name")
    private String name;

    public SimpleAttributeDefinition(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the definition.
     *
     * @return the name, or null if no name exists
     */
    @Override
    public String getName() {
        return name;
    }

}
