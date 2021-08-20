package com.yoti.api.client.docs.session.retrieve.instructions.document;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SelectedIdDocumentResponse.class, name = DocScanConstants.ID_DOCUMENT),
        @JsonSubTypes.Type(value = SelectedSupplementaryDocumentResponse.class, name = DocScanConstants.SUPPLEMENTARY_DOCUMENT)
})
public abstract class SelectedDocumentResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("document_type")
    private String documentType;

    public String getType() {
        return type;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getDocumentType() {
        return documentType;
    }

}
