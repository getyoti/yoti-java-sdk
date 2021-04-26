package com.yoti.api.client.docs.session.retrieve.matching;

import com.yoti.api.client.docs.session.retrieve.CaMatchingStrategyResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FuzzyMatchingStrategyResponse extends CaMatchingStrategyResponse {

    @JsonProperty("fuzziness")
    private double fuzziness;

    public double getFuzziness() {
        return fuzziness;
    }

}
