package com.yoti.api.client;

import java.util.List;

public class AttributeIssuanceDetails {

    private final DateTime expiryDate;

    private final String token;

    private final List<AttributeDefinition> issuingAttributes;

    public AttributeIssuanceDetails(String token, DateTime expiryDate, List<AttributeDefinition> issuingAttributes) {
        if (token == null) {
            this.token = "";
        } else {
            this.token = token;
        }

        this.expiryDate = expiryDate;
        this.issuingAttributes = issuingAttributes;
    }

    public DateTime getExpiryDate() {
        return expiryDate;
    }

    public String getToken() {
        return token;
    }

    public List<AttributeDefinition> getIssuingAttributes() {
        return issuingAttributes;
    }

}
