package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract class WatchlistCheckResponse<ReportResponse extends WatchlistReportResponse<?>> extends ProfileCheckResponse {

    @JsonProperty("report")
    private ReportResponse report;

    @Override
    public ReportResponse getReport() {
        return report;
    }

}
