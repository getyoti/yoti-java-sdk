package com.yoti.api.client.spi.remote.call.share;

import com.yoti.api.client.AttributeDefinition;
import com.yoti.api.client.spi.remote.IssuingAttribute;

import java.util.ArrayList;
import java.util.List;

public class ThirdPartyAttributeIssuingRequestBuilder {

    private String issuanceToken;

    private List<IssuingAttribute> issuingAttributes;

    public ThirdPartyAttributeIssuingRequestBuilder() {
        this.issuingAttributes = new ArrayList<>();
    }

    /**
     * Sets the issuance token to be used with the {@link ThirdPartyAttributeIssuingRequest}
     *
     * @param issuanceToken the issuance token
     * @return the builder
     */
    public ThirdPartyAttributeIssuingRequestBuilder withIssuanceToken(String issuanceToken) {
        this.issuanceToken = issuanceToken;
        return this;
    }

    /**
     * Adds an {@link IssuingAttribute} to be used in the {@link ThirdPartyAttributeIssuingRequest}
     *
     * @param definition the attribute definition
     * @param value      the value of the {@link IssuingAttribute}
     * @return the builder
     */
    public ThirdPartyAttributeIssuingRequestBuilder withIssuingAttribute(AttributeDefinition definition, String value) {
        IssuingAttribute issuingAttribute = new IssuingAttribute(definition, value);
        return withIssuingAttribute(issuingAttribute);
    }

    /**
     * Adds an {@link IssuingAttribute} to be used in the {@link ThirdPartyAttributeIssuingRequest}
     *
     * @param issuingAttribute the issuing attribute
     * @return the builder
     */
    public ThirdPartyAttributeIssuingRequestBuilder withIssuingAttribute(IssuingAttribute issuingAttribute) {
        this.issuingAttributes.add(issuingAttribute);
        return this;
    }

    /**
     * Sets the list of {@link IssuingAttribute}s to be used in the {@link ThirdPartyAttributeIssuingRequest}
     *
     * @param issuingAttributes the list of {@link IssuingAttribute}
     * @return the builder
     */
    public ThirdPartyAttributeIssuingRequestBuilder withIssuingAttributes(List<IssuingAttribute> issuingAttributes) {
        this.issuingAttributes = issuingAttributes;
        return this;
    }

    /**
     * Builds the {@link ThirdPartyAttributeIssuingRequest} using supplied values
     *
     * @return the {@link ThirdPartyAttributeIssuingRequest}
     */
    public ThirdPartyAttributeIssuingRequest build() {
        return new ThirdPartyAttributeIssuingRequest(issuanceToken, issuingAttributes);
    }

}
