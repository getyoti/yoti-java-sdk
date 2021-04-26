package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.docs.DocScanConstants.WITH_CUSTOM_ACCOUNT;
import static com.yoti.api.client.docs.DocScanConstants.WITH_YOTI_ACCOUNT;

import com.yoti.api.client.docs.session.create.check.advanced.RequestedCaMatchingStrategy;
import com.yoti.api.client.docs.session.create.check.advanced.RequestedCaSources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestedYotiAccountWatchlistAdvancedCaConfig.class, name = WITH_YOTI_ACCOUNT),
        @JsonSubTypes.Type(value = RequestedCustomAccountWatchlistAdvancedCaConfig.class, name = WITH_CUSTOM_ACCOUNT)
})
public abstract class RequestedWatchlistAdvancedCaConfig implements RequestedCheckConfig {

    @JsonProperty("remove_deceased")
    private final Boolean removeDeceased;

    @JsonProperty("share_url")
    private final Boolean shareUrl;

    @JsonProperty("sources")
    private final RequestedCaSources sources;

    @JsonProperty("matching_strategy")
    private final RequestedCaMatchingStrategy matchingStrategy;

    RequestedWatchlistAdvancedCaConfig(Boolean removeDeceased, Boolean shareUrl, RequestedCaSources sources, RequestedCaMatchingStrategy matchingStrategy) {
        this.removeDeceased = removeDeceased;
        this.shareUrl = shareUrl;
        this.sources = sources;
        this.matchingStrategy = matchingStrategy;
    }

    public static RequestedYotiAccountWatchlistAdvancedCaConfig.Builder yotiAccountConfigBuilder() {
        return RequestedYotiAccountWatchlistAdvancedCaConfig.builder();
    }

    public static RequestedCustomAccountWatchlistAdvancedCaConfig.Builder customAccountConfigBuilder() {
        return RequestedCustomAccountWatchlistAdvancedCaConfig.builder();
    }

    public Boolean getRemoveDeceased() {
        return removeDeceased;
    }

    public Boolean getShareUrl() {
        return shareUrl;
    }

    public RequestedCaSources getSources() {
        return sources;
    }

    public RequestedCaMatchingStrategy getMatchingStrategy() {
        return matchingStrategy;
    }

    static abstract class Builder<T extends Builder<T>> {

        protected Boolean removeDeceased;
        protected Boolean shareUrl;
        protected RequestedCaSources sources;
        protected RequestedCaMatchingStrategy matchingStrategy;

        protected Builder() {}

        public T withRemoveDeceased(boolean removeDeceased) {
            this.removeDeceased = removeDeceased;
            return (T) this;
        }

        public T withShareUrl(Boolean shareUrl) {
            this.shareUrl = shareUrl;
            return (T) this;
        }

        public T withSources(RequestedCaSources requestedCaSources) {
            this.sources = requestedCaSources;
            return (T) this;
        }

        public T withMatchingStrategy(RequestedCaMatchingStrategy requestedCaMatchingStrategy) {
            this.matchingStrategy = requestedCaMatchingStrategy;
            return (T) this;
        }

        public abstract RequestedWatchlistAdvancedCaConfig build();
    }

}
