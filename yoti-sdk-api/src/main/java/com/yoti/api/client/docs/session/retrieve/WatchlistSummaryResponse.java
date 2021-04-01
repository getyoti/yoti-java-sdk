package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract class WatchlistSummaryResponse<Config extends WatchlistSearchConfigResponse> {

    @JsonProperty("total_hits")
    private int totalHits;

    @JsonProperty("raw_results")
    private RawResultsResponse rawResults;

    @JsonProperty("associated_country_codes")
    private List<String> associatedCountryCodes;

    @JsonProperty("search_config")
    private Config searchConfig;

    public int getTotalHits() {
        return totalHits;
    }

    public RawResultsResponse getRawResults() {
        return rawResults;
    }

    public List<String> getAssociatedCountryCodes() {
        return associatedCountryCodes;
    }

    public Config getSearchConfig() {
        return searchConfig;
    }

}
