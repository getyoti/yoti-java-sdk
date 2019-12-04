package com.yoti.api.client.shareurl.extension;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.AttributeDefinition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ThirdPartyAttributeContent {

    private final Date expiryDate;

    @JsonProperty("definitions")
    private final List<AttributeDefinition> definitions;

    public ThirdPartyAttributeContent(Date expiryDate, List<AttributeDefinition> definitions) {
        this.expiryDate = expiryDate;
        this.definitions = definitions;
    }

    @JsonProperty("expiry_date")
    public String getExpiryDate() {
        SimpleDateFormat rfcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return rfcDateFormat.format(expiryDate.getTime());
    }

    public List<AttributeDefinition> getDefinitions() {
        return definitions;
    }
}
