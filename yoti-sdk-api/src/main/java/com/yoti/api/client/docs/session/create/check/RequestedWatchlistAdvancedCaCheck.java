package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.docs.DocScanConstants.WATCHLIST_ADVANCED_CA;

public class RequestedWatchlistAdvancedCaCheck extends RequestedCheck<RequestedWatchlistAdvancedCaConfig> {

    private final RequestedWatchlistAdvancedCaConfig config;

    RequestedWatchlistAdvancedCaCheck(RequestedWatchlistAdvancedCaConfig config) {
        this.config = config;
    }

    public static RequestedWatchlistAdvancedCaCheck.Builder builder() {
        return new RequestedWatchlistAdvancedCaCheck.Builder();
    }

    @Override
    public String getType() {
        return WATCHLIST_ADVANCED_CA;
    }

    @Override
    public RequestedWatchlistAdvancedCaConfig getConfig() {
        return config;
    }

    public static class Builder {

        private RequestedWatchlistAdvancedCaConfig config;

        private Builder() {}

        public Builder withConfig(RequestedWatchlistAdvancedCaConfig config) {
            this.config = config;
            return this;
        }

        public RequestedWatchlistAdvancedCaCheck build() {
            return new RequestedWatchlistAdvancedCaCheck(config);
        }
    }
}
