package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.Validation.isNullOrEmpty;

import com.yoti.api.client.Date;
import com.yoti.api.client.DocumentDetails;
import com.yoti.api.client.spi.remote.util.Validation;

public final class DocumentDetailsAttributeValue implements DocumentDetails {

    private final String type;
    private final String issuingCountry;
    private final String number;
    private final Date expirationDate;
    private final String authority;

    DocumentDetailsAttributeValue(String type, String issuingCountry, String number, Date expirationDate, String authority) {
        this.type = type;
        this.issuingCountry = issuingCountry;
        this.number = number;
        this.expirationDate = expirationDate;
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

    @Override
    public String toString() {
        String basePattern = "%s %s %s";
        String result = String.format(basePattern, type, issuingCountry, number);

        if (expirationDate != null) {
            result = result + " " + expirationDate;
        }

        if (!isNullOrEmpty(authority)) {
            String separator = expirationDate == null ? " - " : " ";
            result = result + separator + authority;
        }

        return result;
    }

    public static DocumentDetailsValueBuilder builder() {
        return new DocumentDetailsValueBuilder();
    }

    public static class DocumentDetailsValueBuilder {

        private String type;
        private String issuingCountry;
        private String number;
        private Date expirationDate;
        private String authority;

        private DocumentDetailsValueBuilder() {
        }

        public DocumentDetailsValueBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public DocumentDetailsValueBuilder withIssuingCountry(String issuingCountry) {
            this.issuingCountry = issuingCountry;
            return this;
        }

        public DocumentDetailsValueBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public DocumentDetailsValueBuilder withDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public DocumentDetailsValueBuilder withAuthority(String authority) {
            this.authority = authority;
            return this;
        }

        public DocumentDetails build() {
            Validation.notNullOrEmpty(this.type, "type");
            Validation.notNullOrEmpty(this.issuingCountry, "issuingCountry");
            Validation.notNullOrEmpty(this.number, "documentNumber");

            return new DocumentDetailsAttributeValue(type, issuingCountry, number, expirationDate, authority);
        }

    }

}
