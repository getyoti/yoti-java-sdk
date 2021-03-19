package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WatchlistReportResponse extends ReportResponse {

    @JsonProperty("watchlist_summary")
    private WatchlistSummaryResponse watchlistSummary;

    public WatchlistSummaryResponse getWatchlistSummary() {
        return watchlistSummary;
    }

}
