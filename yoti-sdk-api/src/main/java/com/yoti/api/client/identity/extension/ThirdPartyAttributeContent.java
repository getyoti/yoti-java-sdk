package com.yoti.api.client.identity.extension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.yoti.api.client.AttributeDefinition;
import com.yoti.api.client.spi.remote.call.YotiConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThirdPartyAttributeContent {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(YotiConstants.RFC3339_PATTERN_MILLIS);

    private final Date expiryDate;

    @JsonProperty(Property.DEFINITIONS)
    private final List<AttributeDefinition> definitions;

    ThirdPartyAttributeContent(Date expiryDate, List<AttributeDefinition> definitions) {
        this.expiryDate = expiryDate;
        this.definitions = definitions;
    }

    @JsonProperty(Property.EXPIRY_DATE)
    public String getExpiryDate() {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        return DATE_FORMAT.format(expiryDate.getTime());
    }

    public List<AttributeDefinition> getDefinitions() {
        return definitions;
    }

    private static final class Property {

        private static final String DEFINITIONS = "definitions";
        private static final String EXPIRY_DATE = "expiry_date";

    }

}
