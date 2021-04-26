package com.yoti.api.client.docs.session.create.check;

import com.yoti.api.client.docs.session.create.check.advanced.RequestedCaMatchingStrategy;
import com.yoti.api.client.docs.session.create.check.advanced.RequestedCaSources;

public class RequestedYotiAccountWatchlistAdvancedCaConfig extends RequestedWatchlistAdvancedCaConfig {

    RequestedYotiAccountWatchlistAdvancedCaConfig(Boolean removeDeceased,
            Boolean shareUrl,
            RequestedCaSources sources,
            RequestedCaMatchingStrategy matchingStrategy) {
        super(removeDeceased, shareUrl, sources, matchingStrategy);
    }

    public static RequestedYotiAccountWatchlistAdvancedCaConfig.Builder builder() {
        return new RequestedYotiAccountWatchlistAdvancedCaConfig.Builder();
    }

    public static class Builder extends RequestedWatchlistAdvancedCaConfig.Builder<Builder> {

        private Builder() {}

        public RequestedYotiAccountWatchlistAdvancedCaConfig build() {
            return new RequestedYotiAccountWatchlistAdvancedCaConfig(removeDeceased, shareUrl, sources, matchingStrategy);
        }
    }
}
