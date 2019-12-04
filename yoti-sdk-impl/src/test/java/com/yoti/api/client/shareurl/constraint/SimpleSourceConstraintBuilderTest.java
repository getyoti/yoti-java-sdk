package com.yoti.api.client.shareurl.constraint;

import com.yoti.api.client.shareurl.policy.SimpleWantedAnchorBuilder;
import com.yoti.api.client.shareurl.policy.WantedAnchor;
import com.yoti.api.client.shareurl.policy.WantedAnchorBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SimpleSourceConstraintBuilderTest {

    private static final boolean SOME_SOFT_PREFERENCE = true;

    @Mock
    WantedAnchor wantedAnchorMock;

    @Test
    public void shouldBuildSourceConstraintWithGivenValues() {
        SourceConstraint sourceConstraint = new SimpleSourceConstraintBuilder()
                .withWantedAnchor(wantedAnchorMock)
                .withSoftPreference(SOME_SOFT_PREFERENCE)
                .build();

        PreferredSources preferredSources = sourceConstraint.getPreferredSources();

        assertEquals(sourceConstraint.getType(), ConstraintConstants.SOURCE_CONSTRAINT);
        assertThat(preferredSources.getWantedAnchors(), hasSize(1));
        assertThat(preferredSources.getWantedAnchors(), hasItem(wantedAnchorMock));
        assertEquals(preferredSources.getSoftPreference(), SOME_SOFT_PREFERENCE);
    }

    @Test
    public void shouldRetainOrderOfWantedAnchors() {
        WantedAnchor firstWantedAnchor = new SimpleWantedAnchorBuilder()
                .withValue("firstValue")
                .withSubType("")
                .build();

        WantedAnchor secondWantedAnchor = new SimpleWantedAnchorBuilder()
                .withValue("secondValue")
                .withSubType("")
                .build();

        SourceConstraint sourceConstraint = new SimpleSourceConstraintBuilder()
                .withWantedAnchor(firstWantedAnchor)
                .withWantedAnchor(secondWantedAnchor)
                .build();

        PreferredSources result = sourceConstraint.getPreferredSources();

        assertThat(result.getWantedAnchors(), hasSize(2));
        assertEquals(result.getWantedAnchors().get(0).getValue(), "firstValue");
        assertEquals(result.getWantedAnchors().get(1).getValue(), "secondValue");
    }

}
