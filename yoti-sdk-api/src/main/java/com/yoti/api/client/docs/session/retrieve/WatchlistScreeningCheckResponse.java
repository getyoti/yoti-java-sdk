package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WatchlistScreeningCheckResponse extends ProfileCheckResponse {

    @JsonProperty("report")
    private WatchlistReportResponse report;

    @Override
    public WatchlistReportResponse getReport() {
        return report;
    }

}
