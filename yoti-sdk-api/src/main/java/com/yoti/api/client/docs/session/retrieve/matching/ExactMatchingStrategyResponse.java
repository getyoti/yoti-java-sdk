package com.yoti.api.client.docs.session.retrieve.matching;

import com.yoti.api.client.docs.session.retrieve.CaMatchingStrategyResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExactMatchingStrategyResponse extends CaMatchingStrategyResponse {

    @JsonProperty("exact_match")
    private boolean exactMatch;

    public boolean isExactMatch() {
        return exactMatch;
    }

}
