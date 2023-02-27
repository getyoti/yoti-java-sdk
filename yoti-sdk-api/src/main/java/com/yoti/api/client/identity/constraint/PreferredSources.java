package com.yoti.api.client.identity.constraint;

import java.util.List;

import com.yoti.api.client.identity.policy.WantedAnchor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreferredSources {

    @JsonProperty(Property.ANCHORS)
    private final List<WantedAnchor> wantedAnchors;

    @JsonProperty(Property.SOFT_PREFERENCE)
    private final boolean softPreference;

    PreferredSources(List<WantedAnchor> wantedAnchors, boolean softPreference) {
        this.wantedAnchors = wantedAnchors;
        this.softPreference = softPreference;
    }

    public List<WantedAnchor> getWantedAnchors() {
        return wantedAnchors;
    }

    public boolean isSoftPreference() {
        return softPreference;
    }

    private static final class Property {

        private static final String ANCHORS = "anchors";
        private static final String SOFT_PREFERENCE = "soft_preference";

    }

}
