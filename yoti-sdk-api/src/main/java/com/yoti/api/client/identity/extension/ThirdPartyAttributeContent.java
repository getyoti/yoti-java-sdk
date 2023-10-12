package com.yoti.api.client.identity.extension;

import java.time.OffsetDateTime;
import java.util.List;

import com.yoti.api.client.AttributeDefinition;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThirdPartyAttributeContent {

    private final OffsetDateTime expiry;

    @JsonProperty(Property.DEFINITIONS)
    private final List<AttributeDefinition> definitions;

    ThirdPartyAttributeContent(OffsetDateTime expiry, List<AttributeDefinition> definitions) {
        this.expiry = expiry;
        this.definitions = definitions;
    }

    @JsonProperty(Property.EXPIRY_DATE)
    public OffsetDateTime getExpiryDate() {
        return expiry;
    }

    public List<AttributeDefinition> getDefinitions() {
        return definitions;
    }

    private static final class Property {

        private static final String DEFINITIONS = "definitions";
        private static final String EXPIRY_DATE = "expiry_date";

    }

}
