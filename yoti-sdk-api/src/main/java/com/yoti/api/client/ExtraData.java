package com.yoti.api.client;

public interface ExtraData {

    /**
     * Return the credential issuance details associated with the extra
     * data in a receipt.
     *
     * @return the credential issuance details, null if not available
     */
    AttributeIssuanceDetails getAttributeIssuanceDetails();

}
