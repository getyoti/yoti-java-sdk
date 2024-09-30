package com.yoti.api.client.docs.session.retrieve.identityprofile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequirementNotMetDetailsResponse {

    @JsonProperty("failure_type")
    private String failureType;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("document_country_iso_code")
    private String documentCountryIsoCode;

    @JsonProperty("audit_id")
    private String auditId;

    @JsonProperty("details")
    private String details;

    public String getFailureType() {
        return failureType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentCountryIsoCode() {
        return documentCountryIsoCode;
    }

    public String getAuditId() {
        return auditId;
    }

    public String getDetails() {
        return details;
    }

}
