package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class RequestedWatchlistScreeningCheck extends RequestedCheck<RequestedWatchlistScreeningConfig> {

    private final RequestedWatchlistScreeningConfig config;

    RequestedWatchlistScreeningCheck(RequestedWatchlistScreeningConfig config) {
        this.config = config;
    }

    public static RequestedWatchlistScreeningCheck.Builder builder() {
        return new RequestedWatchlistScreeningCheck.Builder();
    }

    @Override
    public String getType() {
        return DocScanConstants.WATCHLIST_SCREENING;
    }

    @Override
    public RequestedWatchlistScreeningConfig getConfig() {
        return config;
    }

    public static class Builder {

        private final RequestedWatchlistScreeningConfig config;

        Builder() {
            config = new RequestedWatchlistScreeningConfig();
        }

        public RequestedWatchlistScreeningCheck build() {
            return new RequestedWatchlistScreeningCheck(config);
        }

    }

}
