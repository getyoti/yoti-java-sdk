package com.yoti.api.client.docs.session.retrieve;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = GeneratedCheckResponse.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GeneratedTextDataCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_TEXT_DATA_CHECK),
        @JsonSubTypes.Type(value = GeneratedSupplementaryDocumentTextDataCheckResponse.class, name = DocScanConstants.SUPPLEMENTARY_DOCUMENT_TEXT_DATA_CHECK)
})
public class GeneratedCheckResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

}
