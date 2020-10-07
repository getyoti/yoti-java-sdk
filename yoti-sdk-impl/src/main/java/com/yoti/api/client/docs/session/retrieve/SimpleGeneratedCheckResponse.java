package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.yoti.api.client.docs.DocScanConstants;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = SimpleGeneratedCheckResponse.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleGeneratedTextDataCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_TEXT_DATA_CHECK),
        @JsonSubTypes.Type(value = SimpleGeneratedSupplementaryDocumentTextDataCheckResponse.class, name = DocScanConstants.SUPPLEMENTARY_DOCUMENT_TEXT_DATA_CHECK)
})
public class SimpleGeneratedCheckResponse implements GeneratedCheckResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

}
