package com.yoti.api.client.spi.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.AttributeDefinition;

public class IssuingAttribute {

    private AttributeDefinition definition;

    @JsonProperty("value")
    private String value;

    public IssuingAttribute(final AttributeDefinition definition, final String value) {
        this.definition = definition;
        this.value = value;
    }

    @JsonProperty("name")
    public String getName() {
        return definition.getName();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
