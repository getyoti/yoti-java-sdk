package com.yoti.api.client.identity.constraint;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreferredSources that = (PreferredSources) o;
        return softPreference == that.softPreference && Objects.equals(wantedAnchors, that.wantedAnchors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(softPreference, wantedAnchors);
    }

    private static final class Property {

        private static final String ANCHORS = "anchors";
        private static final String SOFT_PREFERENCE = "soft_preference";

    }

}
