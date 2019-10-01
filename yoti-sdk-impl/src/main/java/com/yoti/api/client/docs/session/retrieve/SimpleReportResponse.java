package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleReportResponse implements ReportResponse {

    @JsonProperty("recommendation")
    private SimpleRecommendationResponse recommendation;

    @JsonProperty("breakdown")
    private List<SimpleBreakdownResponse> breakdown;

    public RecommendationResponse getRecommendation() {
        return recommendation;
    }

    @Override
    public List<? extends BreakdownResponse> getBreakdown() {
        return breakdown;
    }

}
