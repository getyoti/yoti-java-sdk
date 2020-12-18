package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class RequestedLivenessCheckTest {

    private static final int MAX_RETRIES_VALUE = 3;

    @Test
    public void shouldBuildRequestedLivenessCheck() {
        RequestedLivenessCheck requestedCheck = RequestedLivenessCheck.builder()
                .forLivenessType("SOME_LIVENESS_TYPE")
                .withMaxRetries(MAX_RETRIES_VALUE)
                .build();

        assertThat(requestedCheck, is(instanceOf(RequestedLivenessCheck.class)));
        assertThat(requestedCheck.getConfig(), is(instanceOf(RequestedLivenessConfig.class)));
        assertThat(requestedCheck.getType(), is("LIVENESS"));

        RequestedLivenessConfig configResult = requestedCheck.getConfig();
        assertThat(configResult.getLivenessType(), is("SOME_LIVENESS_TYPE"));
        assertThat(configResult.getMaxRetries(), is(MAX_RETRIES_VALUE));
    }

    @Test
    public void shouldBuildRequestedLivenessCheckWithZoomLivenessType() {
        RequestedLivenessCheck requestedCheck = RequestedLivenessCheck.builder()
                .forZoomLiveness()
                .withMaxRetries(MAX_RETRIES_VALUE)
                .build();

        RequestedLivenessConfig configResult = requestedCheck.getConfig();
        assertThat(configResult.getLivenessType(), is("ZOOM"));
        assertThat(configResult.getMaxRetries(), is(MAX_RETRIES_VALUE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_shouldFailForNullLivenessType() {
        RequestedLivenessCheck.builder()
                .forLivenessType(null)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_shouldFailForEmptyLivenessType() {
        RequestedLivenessCheck.builder()
                .forLivenessType("")
                .build();
    }

}
