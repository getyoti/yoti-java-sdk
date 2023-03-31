package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.hamcrest.Matchers;
import org.junit.Test;

public class RequestedIbvVisualReviewCheckTest {

    @Test
    public void shouldBuildWithManualCheckAlways() {
        RequestedIbvVisualReviewCheck result = RequestedIbvVisualReviewCheck.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedIbvVisualReviewCheck.class)));
        assertThat(result.getConfig(), instanceOf(RequestedIbvVisualReviewConfig.class));
        assertThat(result.getType(), Matchers.is("IBV_VISUAL_REVIEW_CHECK"));

        RequestedIbvVisualReviewConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("ALWAYS"));
    }

    @Test
    public void shouldBuildWithManualCheckFallback() {
        RequestedIbvVisualReviewCheck result = RequestedIbvVisualReviewCheck.builder()
                .withManualCheckFallback()
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedIbvVisualReviewCheck.class)));
        assertThat(result.getConfig(), Matchers.is(instanceOf(RequestedIbvVisualReviewConfig.class)));
        assertThat(result.getType(), Matchers.is("IBV_VISUAL_REVIEW_CHECK"));

        RequestedIbvVisualReviewConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("FALLBACK"));
    }

    @Test
    public void shouldBuildWithManualCheckNever() {
        RequestedIbvVisualReviewCheck result = RequestedIbvVisualReviewCheck.builder()
                .withManualCheckNever()
                .build();

        assertThat(result, Matchers.is(instanceOf(RequestedIbvVisualReviewCheck.class)));
        assertThat(result.getConfig(), Matchers.is(instanceOf(RequestedIbvVisualReviewConfig.class)));
        assertThat(result.getType(), Matchers.is("IBV_VISUAL_REVIEW_CHECK"));

        RequestedIbvVisualReviewConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), Matchers.is("NEVER"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseForNullManualCheckType() {
        RequestedIbvVisualReviewCheck.builder()
                .build();
    }

}