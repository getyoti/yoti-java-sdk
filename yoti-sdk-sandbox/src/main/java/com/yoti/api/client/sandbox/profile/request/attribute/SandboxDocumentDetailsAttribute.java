package com.yoti.api.client.sandbox.profile.request.attribute;

import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;

public final class SandboxDocumentDetailsAttribute implements DocumentDetails {

    private final DocumentType type;
    private final String issuingCountry;
    private final String number;
    private final Date expirationDate;
    private final String authority;

    public SandboxDocumentDetailsAttribute(DocumentType type, String issuingCountry, String number, Date expirationDate, String issuingAuthority) {
        this.type = type;
        this.issuingCountry = issuingCountry;
        this.number = number;
        this.expirationDate = expirationDate;
        this.authority = issuingAuthority;
    }


    @Override
    public DocumentType getType() {
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
