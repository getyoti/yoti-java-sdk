package com.yoti.api.client.docs.session.create.check;

import static com.yoti.api.client.docs.DocScanConstants.ADVERSE_MEDIA;
import static com.yoti.api.client.docs.DocScanConstants.SANCTIONS;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedWatchlistScreeningConfig implements RequestedCheckConfig {

    @JsonProperty("categories")
    private final List<String> categories;

    RequestedWatchlistScreeningConfig(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Set<String> categories;

        private Builder() {
        }

        public Builder withAdverseMediaCategory() {
            return withCategory(ADVERSE_MEDIA);
        }

        public Builder withSanctionsCategory() {
            return withCategory(SANCTIONS);
        }

        public Builder withCategory(String category) {
            notNull(category, "category");
            if (categories == null) {
                categories = new TreeSet<>();
            }
            categories.add(category);
            return this;
        }

        public RequestedWatchlistScreeningConfig build() {
            List<String> categoriesList = categories == null ? null : new ArrayList<>(categories);
            return new RequestedWatchlistScreeningConfig(categoriesList);
        }

    }

}
