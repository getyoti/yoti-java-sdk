package com.yoti.api.client.shareurl.constraint;

import java.util.List;

import com.yoti.api.client.shareurl.policy.WantedAnchor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PreferredSources {

    @JsonProperty("anchors")
    private final List<WantedAnchor> wantedAnchors;

    @JsonProperty("soft_preference")
    private final boolean softPreference;

    PreferredSources(List<WantedAnchor> wantedAnchors, boolean softPreference) {
        this.wantedAnchors = wantedAnchors;
        this.softPreference = softPreference;
    }

    /**
     * Get the list of {@link WantedAnchor}
     *
     * @return the list of {@link WantedAnchor}
     */
    public List<WantedAnchor> getWantedAnchors() {
        return wantedAnchors;
    }

    /**
     * Get the soft preference
     *
     * @return the soft preference
     */
    public boolean getSoftPreference() {
        return softPreference;
    }

}
