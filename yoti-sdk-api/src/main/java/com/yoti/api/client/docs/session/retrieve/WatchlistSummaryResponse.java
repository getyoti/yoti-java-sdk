package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WatchlistSummaryResponse {

    @JsonProperty("total_hits")
    private int totalHits;

    @JsonProperty("search_config")
    private SearchConfigResponse searchConfig;

    @JsonProperty("raw_results")
    private RawResultsResponse rawResults;

    @JsonProperty("associated_country_codes")
    private List<String> associatedCountryCodes;

    public int getTotalHits() {
        return totalHits;
    }

    public SearchConfigResponse getSearchConfig() {
        return searchConfig;
    }

    public RawResultsResponse getRawResults() {
        return rawResults;
    }

    public List<String> getAssociatedCountryCodes() {
        return associatedCountryCodes;
    }

}
