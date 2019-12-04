package com.yoti.api.client.shareurl.constraint;

import com.yoti.api.client.shareurl.policy.WantedAnchor;

public interface SourceConstraintBuilder extends ConstraintBuilder {

    SourceConstraintBuilder withWantedAnchor(WantedAnchor wantedAnchor);

    SourceConstraintBuilder withSoftPreference(boolean softPreference);

    SourceConstraint build();

}
