package com.yoti.api.client.docs.session.retrieve.instructions.branch;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UkPostOfficeBranchResponse.class, name = DocScanConstants.UK_POST_OFFICE)
})
public class BranchResponse {

    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }

}
