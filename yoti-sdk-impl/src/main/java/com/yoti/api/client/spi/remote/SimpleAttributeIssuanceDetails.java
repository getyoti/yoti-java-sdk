package com.yoti.api.client.spi.remote;

import com.yoti.api.client.DateTime;
import com.yoti.api.client.AttributeIssuanceDetails;
import com.yoti.api.client.AttributeDefinition;

import java.util.List;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

public class SimpleAttributeIssuanceDetails implements AttributeIssuanceDetails {

    private DateTime expiryDate;

    private String token;

    private List<AttributeDefinition> issuingAttributes;

    public SimpleAttributeIssuanceDetails(String token, DateTime expiryDate, List<AttributeDefinition> issuingAttributes) {
        if (token == null) {
            this.token = "";
        } else {
            this.token = token;
        }

        this.expiryDate = expiryDate;
        this.issuingAttributes = issuingAttributes;
    }

    @Override
    public DateTime getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public List<AttributeDefinition> getIssuingAttributes() {
        return issuingAttributes;
    }

}
