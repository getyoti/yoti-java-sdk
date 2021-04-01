package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract class WatchlistReportResponse<Summary extends WatchlistSummaryResponse<?>> extends ReportResponse {

    @JsonProperty("watchlist_summary")
    private Summary watchlistSummary;

    public Summary getWatchlistSummary() {
        return watchlistSummary;
    }

}
