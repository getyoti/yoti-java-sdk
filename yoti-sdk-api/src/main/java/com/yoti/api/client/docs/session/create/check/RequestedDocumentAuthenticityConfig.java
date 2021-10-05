package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration applied when creating a DocumentAuthenticityCheck
 */
public class RequestedDocumentAuthenticityConfig implements RequestedCheckConfig {

    @JsonProperty("manual_check")
    private final String manualCheck;

    @JsonProperty("issuing_authority_sub_check")
    private final IssuingAuthoritySubCheck issuingAuthoritySubCheck;

    public RequestedDocumentAuthenticityConfig(String manualCheck) {
        this.manualCheck = manualCheck;
        this.issuingAuthoritySubCheck = null;
    }

    public RequestedDocumentAuthenticityConfig(String manualCheck, IssuingAuthoritySubCheck issuingAuthoritySubCheck) {
        this.manualCheck = manualCheck;
        this.issuingAuthoritySubCheck = issuingAuthoritySubCheck;
    }

    /**
     * Returns the value for a manual check for a given
     * document authenticity check.
     *
     * @return the manual check value
     */
    public String getManualCheck() {
        return manualCheck;
    }

    /**
     * Returns the requested Issuing Authority sub check
     *
     * @return the issuing authority sub check
     */
    public IssuingAuthoritySubCheck getIssuingAuthoritySubCheck() {
        return issuingAuthoritySubCheck;
    }

}
