package com.yoti.api.client.identity.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yoti.api.client.identity.policy.WantedAnchor;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceConstraint implements Constraint {

    @JsonProperty(Property.TYPE)
    private final String type;

    @JsonProperty(Property.PREFERRED_SOURCES)
    private final PreferredSources preferredSources;

    SourceConstraint(List<WantedAnchor> wantedAnchors, boolean softPreference) {
        type = "SOURCE";
        preferredSources = new PreferredSources(wantedAnchors, softPreference);
    }

    @Override
    public String getType() {
        return type;
    }

    public PreferredSources getPreferredSources() {
        return preferredSources;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<WantedAnchor> wantedAnchors;
        private boolean softPreference;

        private Builder() {
            this.wantedAnchors = new ArrayList<>();
        }

        public Builder withWantedAnchors(List<WantedAnchor> wantedAnchors) {
            this.wantedAnchors = Collections.unmodifiableList(wantedAnchors);
            return this;
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
            return new SourceConstraint(Collections.unmodifiableList(wantedAnchors),  softPreference);
        }

    }

    private static final class Property {

        private static final String TYPE = "type";
        private static final String PREFERRED_SOURCES = "preferred_sources";

    }

}
