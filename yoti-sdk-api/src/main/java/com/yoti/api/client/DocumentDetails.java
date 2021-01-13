package com.yoti.api.client;

import static com.yoti.api.client.spi.remote.util.Validation.isNullOrEmpty;

import com.yoti.api.client.spi.remote.util.Validation;

/**
 * Details of the document used to create the Yoti account.
 */
public class DocumentDetails {

    public static final String DOCUMENT_TYPE_PASSPORT = "PASSPORT";
    public static final String DOCUMENT_TYPE_DRIVING_LICENCE = "DRIVING_LICENCE";
    public static final String DOCUMENT_TYPE_AADHAAR = "AADHAAR";
    public static final String DOCUMENT_TYPE_PASS_CARD = "PASS_CARD";

    private final String type;
    private final String issuingCountry;
    private final String number;
    private final Date expirationDate;
    private final String authority;

    DocumentDetails(String type, String issuingCountry, String number, Date expirationDate, String authority) {
        this.type = type;
        this.issuingCountry = issuingCountry;
        this.number = number;
        this.expirationDate = expirationDate;
        this.authority = authority;
    }

    /**
     * Return document type.
     *
     * @return Return document type
     */
    public String getType() {
        return type;
    }

    /**
     * Return issuing country
     *
     * @return Return issuing country
     */
    public String getIssuingCountry() {
        return issuingCountry;
    }

    /**
     * Return issuing country, may contain letters.
     *
     * @return Return issuing country, may contain letters.
     */
    public String getDocumentNumber() {
        return number;
    }

    /**
     * The date of expiration for the document.
     *
     * @return The date of expiration for the document.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Either a country code, or the name of the issuing authority
     *
     * @return Either a country code, or the name of the issuing authority
     */
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

    public static DocumentDetails.Builder builder() {
        return new DocumentDetails.Builder();
    }

    public static class Builder {

        private String type;
        private String issuingCountry;
        private String number;
        private Date expirationDate;
        private String authority;

        private Builder() {
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withIssuingCountry(String issuingCountry) {
            this.issuingCountry = issuingCountry;
            return this;
        }

        public Builder withNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder withDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder withAuthority(String authority) {
            this.authority = authority;
            return this;
        }

        public DocumentDetails build() {
            Validation.notNullOrEmpty(this.type, "type");
            Validation.notNullOrEmpty(this.issuingCountry, "issuingCountry");
            Validation.notNullOrEmpty(this.number, "documentNumber");

            return new DocumentDetails(type, issuingCountry, number, expirationDate, authority);
        }

    }

}
