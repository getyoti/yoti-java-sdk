package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleRequestedLivenessCheckBuilderTest {

    private static final int MAX_RETRIES_VALUE = 3;

    @Test
    public void shouldBuildSimpleRequestedLivenessCheck() {
        RequestedCheck requestedCheck = new SimpleRequestedLivenessCheckBuilder()
                .forLivenessType("SOME_LIVENESS_TYPE")
                .withMaxRetries(MAX_RETRIES_VALUE)
                .build();

        assertThat(requestedCheck, is(instanceOf(SimpleRequestedLivenessCheck.class)));
        assertThat(requestedCheck.getConfig(), is(instanceOf(SimpleRequestedLivenessConfig.class)));
        assertThat(requestedCheck.getType(), is("LIVENESS"));

        SimpleRequestedLivenessConfig configResult = (SimpleRequestedLivenessConfig) requestedCheck.getConfig();
        assertThat(configResult.getLivenessType(), is("SOME_LIVENESS_TYPE"));
        assertThat(configResult.getMaxRetries(), is(MAX_RETRIES_VALUE));
    }

    @Test
    public void shouldBuildSimpleRequestedLivenessCheckWithZoomLivenessType() {
        RequestedCheck requestedCheck = new SimpleRequestedLivenessCheckBuilder()
                .forZoomLiveness()
                .withMaxRetries(MAX_RETRIES_VALUE)
                .build();

        SimpleRequestedLivenessConfig configResult = (SimpleRequestedLivenessConfig) requestedCheck.getConfig();
        assertThat(configResult.getLivenessType(), is("ZOOM"));
        assertThat(configResult.getMaxRetries(), is(MAX_RETRIES_VALUE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_shouldFailForNullLivenessType() {
        new SimpleRequestedLivenessCheckBuilder()
                .forLivenessType(null)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_shouldFailForEmptyLivenessType() {
        new SimpleRequestedLivenessCheckBuilder()
                .forLivenessType("")
                .build();
    }

}
