package com.yoti.api.client.spi.remote;

import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;

final class DocumentDetailsAttributeValue implements DocumentDetails {

    private final String type;
    private final String issuingCountry;
    private final Date expirationDate;
    private final String number;
    private final String authority;

    public DocumentDetailsAttributeValue(String type, String issuingCountry, Date expirationDate, String number, String authority) {
        this.type = type;
        this.issuingCountry = issuingCountry;
        this.expirationDate = expirationDate;
        this.number = number;
        this.authority = authority;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getIssuingCountry() {
        return issuingCountry;
    }

    @Override
    public String getDocumentNumber() {
        return number;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String getIssuingAuthority() {
        return authority;
    }

}
