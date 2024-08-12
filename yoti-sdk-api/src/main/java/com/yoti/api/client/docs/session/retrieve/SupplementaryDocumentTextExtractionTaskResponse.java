package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SupplementaryDocumentTextExtractionTaskResponse extends TaskResponse {

    @JsonProperty("recommendation")
    private TaskRecommendationResponse recommendation;

    /**
     * The recommendation for the task
     *
     * @return the recommendation
     */
    public TaskRecommendationResponse getRecommendation() {
        return recommendation;
    }

    public List<GeneratedSupplementaryDocumentTextDataCheckResponse> getGeneratedTextDataChecks() {
        return filterGeneratedChecksByType(GeneratedSupplementaryDocumentTextDataCheckResponse.class);
    }

}
