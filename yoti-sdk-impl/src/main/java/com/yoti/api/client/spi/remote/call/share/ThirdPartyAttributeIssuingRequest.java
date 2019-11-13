package com.yoti.api.client.spi.remote.call.share;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.AttributeDefinition;
import com.yoti.api.client.spi.remote.IssuingAttribute;

import java.util.ArrayList;
import java.util.List;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

public class ThirdPartyAttributeIssuingRequest {

    @JsonProperty("issuance_token")
    private final String issuanceToken;

    @JsonProperty("attributes")
    private final List<IssuingAttribute> issuingAttributes;

    public ThirdPartyAttributeIssuingRequest(String issuanceToken, List<IssuingAttribute> issuingAttributes) {
        notNullOrEmpty(issuanceToken, "issuanceToken");

        this.issuanceToken = issuanceToken;
        this.issuingAttributes = issuingAttributes;
    }

    public String getIssuanceToken() {
        return issuanceToken;
    }

    public List<IssuingAttribute> getIssuingAttributes() {
        return issuingAttributes;
    }


}
