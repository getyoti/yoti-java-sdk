package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface ReportResponse {

    RecommendationResponse getRecommendation();

    List<? extends BreakdownResponse> getBreakdown();

}
