package com.yoti.api.client.docs.session.retrieve;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomAccountWatchlistCaSearchConfigResponse extends WatchlistAdvancedCaSearchConfigResponse {

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("monitoring")
    private boolean monitoring;

    @JsonProperty("tags")
    private Map<String, String> tags;

    @JsonProperty("client_ref")
    private String clientRef;

    public String getApiKey() {
        return apiKey;
    }

    public boolean isMonitoring() {
        return monitoring;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public String getClientRef() {
        return clientRef;
    }
}
