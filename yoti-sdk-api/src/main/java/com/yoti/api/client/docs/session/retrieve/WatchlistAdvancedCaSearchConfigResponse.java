package com.yoti.api.client.docs.session.retrieve;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = YotiAccountWatchlistCaSearchConfigResponse.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = YotiAccountWatchlistCaSearchConfigResponse.class, name = DocScanConstants.WITH_YOTI_ACCOUNT),
        @JsonSubTypes.Type(value = CustomAccountWatchlistCaSearchConfigResponse.class, name = DocScanConstants.WITH_CUSTOM_ACCOUNT)
})
public abstract class WatchlistAdvancedCaSearchConfigResponse extends WatchlistSearchConfigResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("remove_deceased")
    private boolean removeDeceased;

    @JsonProperty("share_url")
    private boolean shareUrl;

    @JsonProperty("sources")
    private CaSourcesResponse sources;

    @JsonProperty("matching_strategy")
    private CaMatchingStrategyResponse matchingStrategy;

    public boolean isRemoveDeceased() {
        return removeDeceased;
    }

    public boolean isShareUrl() {
        return shareUrl;
    }

    public CaSourcesResponse getSources() {
        return sources;
    }

    public CaMatchingStrategyResponse getMatchingStrategy() {
        return matchingStrategy;
    }
}
