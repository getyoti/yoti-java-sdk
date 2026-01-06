package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.DocScanConstants;

public class RequestedWatchlistScreeningCheck extends RequestedCheck<RequestedWatchlistScreeningConfig> {

    private final RequestedWatchlistScreeningConfig config;

    private RequestedWatchlistScreeningCheck(RequestedWatchlistScreeningConfig config) {
        this.config = config;
    }

    @Override
    public String getType() {
        return DocScanConstants.WATCHLIST_SCREENING;
    }

    @Override
    public RequestedWatchlistScreeningConfig getConfig() {
        return config;
    }

    public static RequestedWatchlistScreeningCheck.Builder builder() {
        return new RequestedWatchlistScreeningCheck.Builder();
    }

    public static class Builder {

        private RequestedWatchlistScreeningConfig config;

        private Builder() {
        }

        public Builder withConfig(RequestedWatchlistScreeningConfig config) {
            this.config = config;
            return this;
        }

        public RequestedWatchlistScreeningCheck build() {
            return new RequestedWatchlistScreeningCheck(config);
        }

    }

}
