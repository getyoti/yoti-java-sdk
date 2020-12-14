package com.yoti.api.client.spi.remote.call.share;

import static com.yoti.api.client.spi.remote.util.Validation.notNullOrEmpty;

import java.util.List;

import com.yoti.api.client.spi.remote.IssuingAttribute;

import com.fasterxml.jackson.annotation.JsonProperty;

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
