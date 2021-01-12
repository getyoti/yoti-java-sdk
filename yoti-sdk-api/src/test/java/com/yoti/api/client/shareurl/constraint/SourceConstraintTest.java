package com.yoti.api.client.shareurl.constraint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;

import com.yoti.api.client.shareurl.policy.WantedAnchor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SourceConstraintTest {

    private static final boolean SOME_SOFT_PREFERENCE = true;

    @Mock
    WantedAnchor wantedAnchorMock;

    @Test
    public void shouldBuildSourceConstraintWithGivenValues() {
        SourceConstraint sourceConstraint = SourceConstraint.builder()
                .withWantedAnchor(wantedAnchorMock)
                .withSoftPreference(SOME_SOFT_PREFERENCE)
                .build();

        PreferredSources preferredSources = sourceConstraint.getPreferredSources();

        assertEquals(ConstraintConstants.SOURCE_CONSTRAINT, sourceConstraint.getType());
        assertThat(preferredSources.getWantedAnchors(), hasSize(1));
        assertThat(preferredSources.getWantedAnchors(), hasItem(wantedAnchorMock));
        assertEquals(SOME_SOFT_PREFERENCE, preferredSources.getSoftPreference());
    }

    @Test
    public void shouldRetainOrderOfWantedAnchors() {
        WantedAnchor firstWantedAnchor = WantedAnchor.builder()
                .withValue("firstValue")
                .withSubType("")
                .build();

        WantedAnchor secondWantedAnchor = WantedAnchor.builder()
                .withValue("secondValue")
                .withSubType("")
                .build();

        SourceConstraint sourceConstraint = SourceConstraint.builder()
                .withWantedAnchor(firstWantedAnchor)
                .withWantedAnchor(secondWantedAnchor)
                .build();

        PreferredSources result = sourceConstraint.getPreferredSources();

        assertThat(result.getWantedAnchors(), hasSize(2));
        assertEquals("firstValue", result.getWantedAnchors().get(0).getValue());
        assertEquals("secondValue", result.getWantedAnchors().get(1).getValue());
    }

}
