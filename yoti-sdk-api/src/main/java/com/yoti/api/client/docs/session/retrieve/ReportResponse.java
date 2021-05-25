package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportResponse {

    @JsonProperty("recommendation")
    private RecommendationResponse recommendation;

    @JsonProperty("breakdown")
    private List<BreakdownResponse> breakdown;

    public RecommendationResponse getRecommendation() {
        return recommendation;
    }

    public List<? extends BreakdownResponse> getBreakdown() {
        return breakdown;
    }

}
