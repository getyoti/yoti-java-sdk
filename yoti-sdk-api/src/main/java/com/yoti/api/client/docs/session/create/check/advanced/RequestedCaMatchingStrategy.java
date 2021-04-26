package com.yoti.api.client.docs.session.create.check.advanced;

import static com.yoti.api.client.docs.DocScanConstants.EXACT;
import static com.yoti.api.client.docs.DocScanConstants.FUZZY;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestedExactMatchingStrategy.class, name = EXACT),
        @JsonSubTypes.Type(value = RequestedFuzzyMatchingStrategy.class, name = FUZZY)
})
public abstract class RequestedCaMatchingStrategy {

    public static RequestedExactMatchingStrategy.Builder forExactMatchBuilder() {
        return RequestedExactMatchingStrategy.builder();
    }

    public static RequestedFuzzyMatchingStrategy.Builder forFuzzyMatchBuilder() {
        return RequestedFuzzyMatchingStrategy.builder();
    }
}
