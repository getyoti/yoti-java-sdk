package com.yoti.api.client.shareurl.constraint;

import com.yoti.api.client.shareurl.policy.WantedAnchor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleSourceConstraintBuilder implements SourceConstraintBuilder {

    private List<WantedAnchor> wantedAnchors;

    private boolean softPreference;

    SimpleSourceConstraintBuilder() {
        this.wantedAnchors = new ArrayList<>();
        this.softPreference = false;
    }

    @Override
    public SourceConstraintBuilder withWantedAnchor(WantedAnchor wantedAnchor) {
        this.wantedAnchors.add(wantedAnchor);
        return this;
    }

    @Override
    public SourceConstraintBuilder withSoftPreference(boolean softPreference) {
        this.softPreference = softPreference;
        return this;
    }

    @Override
    public SourceConstraint build() {
        return new SimpleSourceConstraint(
                Collections.unmodifiableList(wantedAnchors),
                softPreference
        );
    }
}
