package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WatchlistSummaryResponse {

    @JsonProperty("total_hits")
    private int totalHits;

    @JsonProperty("search_config")
    private SearchConfigResponse searchConfig;

    @JsonProperty("raw_results")
    private RawResultsResponse rawResults;

    public int getTotalHits() {
        return totalHits;
    }

    public SearchConfigResponse getSearchConfig() {
        return searchConfig;
    }

    public RawResultsResponse getRawResults() {
        return rawResults;
    }

}
