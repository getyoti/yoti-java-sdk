package com.yoti.api.client.shareurl.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.shareurl.policy.WantedAnchor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceConstraint implements Constraint {

    @JsonProperty("type")
    private static final String type = ConstraintConstants.SOURCE_CONSTRAINT;

    @JsonProperty("preferred_sources")
    private final PreferredSources preferredSources;

    SourceConstraint(List<WantedAnchor> wantedAnchors, boolean softPreference) {
        this.preferredSources = new PreferredSources(wantedAnchors, softPreference);
    }

    public static SourceConstraint.Builder builder() {
        return new SourceConstraint.Builder();
    }

    @Override
    public String getType() {
        return type;
    }

    public PreferredSources getPreferredSources() {
        return preferredSources;
    }

    public static class Builder {

        private final List<WantedAnchor> wantedAnchors;
        private boolean softPreference;

        private Builder() {
            this.wantedAnchors = new ArrayList<>();
            this.softPreference = false;
        }

        public Builder withWantedAnchor(WantedAnchor wantedAnchor) {
            this.wantedAnchors.add(wantedAnchor);
            return this;
        }

        public Builder withSoftPreference(boolean softPreference) {
            this.softPreference = softPreference;
            return this;
        }

        public SourceConstraint build() {
            return new SourceConstraint(
                    Collections.unmodifiableList(wantedAnchors),
                    softPreference
            );
        }

    }

}
