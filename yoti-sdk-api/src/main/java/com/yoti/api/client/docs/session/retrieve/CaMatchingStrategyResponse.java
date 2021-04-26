package com.yoti.api.client.docs.session.retrieve;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.docs.session.retrieve.matching.ExactMatchingStrategyResponse;
import com.yoti.api.client.docs.session.retrieve.matching.FuzzyMatchingStrategyResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ExactMatchingStrategyResponse.class, name = DocScanConstants.EXACT),
        @JsonSubTypes.Type(value = FuzzyMatchingStrategyResponse.class, name = DocScanConstants.FUZZY),
})
public abstract class CaMatchingStrategyResponse {

    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }
}
