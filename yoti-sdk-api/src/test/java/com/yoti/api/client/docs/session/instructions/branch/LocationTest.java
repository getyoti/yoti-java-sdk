package com.yoti.api.client.docs.session.instructions.branch;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class LocationTest {

    private static final double SOME_LATITUDE = -40.4837D;
    private static final double SOME_LONGITUDE = 90.47483D;

    @Test
    public void builder_shouldBuildWithLatitude() {
        Location result = Location.builder()
                .withLatitude(SOME_LATITUDE)
                .build();

        assertThat(result.getLatitude(), is(SOME_LATITUDE));
        assertThat(result.getLongitude(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithLongitude() {
        Location result = Location.builder()
                .withLongitude(SOME_LONGITUDE)
                .build();

        assertThat(result.getLatitude(), is(nullValue()));
        assertThat(result.getLongitude(), is(SOME_LONGITUDE));
    }

    @Test
    public void builder_shouldBuildWithAllProperties() {
        Location result = Location.builder()
                .withLatitude(SOME_LATITUDE)
                .withLongitude(SOME_LONGITUDE)
                .build();

        assertThat(result.getLatitude(), is(SOME_LATITUDE));
        assertThat(result.getLongitude(), is(SOME_LONGITUDE));
    }

}
