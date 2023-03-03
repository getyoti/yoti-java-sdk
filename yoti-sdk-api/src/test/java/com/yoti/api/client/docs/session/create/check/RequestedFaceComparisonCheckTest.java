package com.yoti.api.client.docs.session.create.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class RequestedFaceComparisonCheckTest {

    @Test
    public void shouldBuildWithManualCheckAlways() {
        RequestedFaceComparisonCheck result = RequestedFaceComparisonCheck.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result, is(instanceOf(RequestedFaceComparisonCheck.class)));
        assertThat(result.getConfig(), instanceOf(RequestedFaceComparisonConfig.class));
        assertThat(result.getType(), is("FACE_COMPARISON"));

        RequestedFaceComparisonConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildWithManualCheckFallback() {
        RequestedFaceComparisonCheck result = RequestedFaceComparisonCheck.builder()
                .withManualCheckFallback()
                .build();

        assertThat(result, is(instanceOf(RequestedFaceComparisonCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(RequestedFaceComparisonConfig.class)));
        assertThat(result.getType(), is("FACE_COMPARISON"));

        RequestedFaceComparisonConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildWithManualCheckNever() {
        RequestedFaceComparisonCheck result = RequestedFaceComparisonCheck.builder()
                .withManualCheckNever()
                .build();

        assertThat(result, is(instanceOf(RequestedFaceComparisonCheck.class)));
        assertThat(result.getConfig(), is(instanceOf(RequestedFaceComparisonConfig.class)));
        assertThat(result.getType(), is("FACE_COMPARISON"));

        RequestedFaceComparisonConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("NEVER"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRaiseForNullManualCheckType() {
        RequestedFaceComparisonCheck.builder()
                .build();
    }

}
