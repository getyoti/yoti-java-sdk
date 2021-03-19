package com.yoti.api.client.docs.session.create.check;

import java.util.ArrayList;
import java.util.List;

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

        private static final String ADVERSE_MEDIA = "ADVERSE-MEDIA";
        private static final String SANCTIONS = "SANCTIONS";

        private List<String> categories;

        public Builder withAdverseMediaCategory() {
            return withCategory(ADVERSE_MEDIA);
        }

        public Builder withSanctionsCategory() {
            return withCategory(SANCTIONS);
        }

        public Builder withCategory(String category) {
            if (categories == null) {
                categories = new ArrayList<>();
            }
            if (!categories.contains(category)) {
                categories.add(category);
            }
            return this;
        }

        public RequestedWatchlistScreeningCheck build() {
            RequestedWatchlistScreeningConfig config = new RequestedWatchlistScreeningConfig(categories);
            return new RequestedWatchlistScreeningCheck(config);
        }

    }

}
