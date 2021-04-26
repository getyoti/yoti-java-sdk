package com.yoti.api.client.docs.session.create.check;

import java.util.Map;

import com.yoti.api.client.docs.session.create.check.advanced.RequestedCaMatchingStrategy;
import com.yoti.api.client.docs.session.create.check.advanced.RequestedCaSources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedCustomAccountWatchlistAdvancedCaConfig extends RequestedWatchlistAdvancedCaConfig {

    @JsonProperty("api_key")
    private final String apiKey;

    @JsonProperty("monitoring")
    private final Boolean monitoring;

    @JsonProperty("tags")
    private final Map<String, String> tags;

    @JsonProperty("client_ref")
    private final String clientRef;

    RequestedCustomAccountWatchlistAdvancedCaConfig(Boolean removeDeceased,
            Boolean shareUrl,
            RequestedCaSources sources,
            RequestedCaMatchingStrategy matchingStrategy,
            String apiKey,
            Boolean monitoring,
            Map<String, String> tags,
            String clientRef) {
        super(removeDeceased, shareUrl, sources, matchingStrategy);
        this.apiKey = apiKey;
        this.monitoring = monitoring;
        this.tags = tags;
        this.clientRef = clientRef;
    }

    public static RequestedCustomAccountWatchlistAdvancedCaConfig.Builder builder() {
        return new RequestedCustomAccountWatchlistAdvancedCaConfig.Builder();
    }

    public String getApiKey() {
        return apiKey;
    }

    public Boolean getMonitoring() {
        return monitoring;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public String getClientRef() {
        return clientRef;
    }

    public static class Builder extends RequestedWatchlistAdvancedCaConfig.Builder<Builder> {

        private String apiKey;
        private Boolean monitoring;
        private Map<String, String> tags;
        private String clientRef;

        private Builder() {}

        public Builder withApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder withMonitoring(boolean monitoring) {
            this.monitoring = monitoring;
            return this;
        }

        public Builder withTags(Map<String, String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withClientRef(String clientRef) {
            this.clientRef = clientRef;
            return this;
        }

        public RequestedCustomAccountWatchlistAdvancedCaConfig build() {
            return new RequestedCustomAccountWatchlistAdvancedCaConfig(removeDeceased,
                                                                       shareUrl,
                                                                       sources,
                                                                       matchingStrategy,
                                                                       apiKey,
                                                                       monitoring,
                                                                       tags,
                                                                       clientRef);
        }
    }
}
