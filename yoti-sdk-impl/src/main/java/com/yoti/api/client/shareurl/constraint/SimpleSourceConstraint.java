package com.yoti.api.client.shareurl.constraint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.shareurl.policy.WantedAnchor;

import java.util.List;

public class SimpleSourceConstraint implements SourceConstraint {

    @JsonProperty("type")
    private static final String type = ConstraintConstants.SOURCE_CONSTRAINT;

    @JsonProperty("preferred_sources")
    private PreferredSources preferredSources;

    SimpleSourceConstraint(List<WantedAnchor> wantedAnchors, boolean softPreference) {
        this.preferredSources = new SimplePreferredSources(wantedAnchors, softPreference);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public PreferredSources getPreferredSources() {
        return preferredSources;
    }

    public static final class SimplePreferredSources implements PreferredSources {

        @JsonProperty("anchors")
        private List<WantedAnchor> wantedAnchors;

        @JsonProperty("soft_preference")
        private boolean softPreference;

        public SimplePreferredSources(List<WantedAnchor> wantedAnchors, boolean softPreference) {
            this.wantedAnchors = wantedAnchors;
            this.softPreference = softPreference;
        }

        public List<WantedAnchor> getWantedAnchors() {
            return wantedAnchors;
        }

        public boolean getSoftPreference() {
            return softPreference;
        }

    }
}
