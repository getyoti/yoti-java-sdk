package com.yoti.api.client;

/**
 * Details of the document used to create the Yoti account.
 *
 */
public interface DocumentDetails {

    String DOCUMENT_TYPE_PASSPORT = "PASSPORT";
    String DOCUMENT_TYPE_DRIVING_LICENCE = "DRIVING_LICENCE";
    String DOCUMENT_TYPE_AADHAAR = "AADHAAR";
    String DOCUMENT_TYPE_PASS_CARD = "PASS_CARD";

    /**
     * Return document type.
     * 
     * @return Return document type
     */
    String getType();

    /**
     * Return issuing country
     * 
     * @return Return issuing country
     */
    String getIssuingCountry();

    /**
     * Return issuing country, may contain letters.
     * 
     * @return Return issuing country, may contain letters.
     */
    String getDocumentNumber();

    /**
     * The date of expiration for the document.
     * 
     * @return The date of expiration for the document.
     */
    Date getExpirationDate();

    /**
     * Either a country code, or the name of the issuing authority
     *
     * @return Either a country code, or the name of the issuing authority
     */
    String getIssuingAuthority();

}
