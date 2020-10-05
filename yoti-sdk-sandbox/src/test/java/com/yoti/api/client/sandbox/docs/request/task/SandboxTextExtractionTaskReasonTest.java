package com.yoti.api.client.sandbox.docs.request.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SandboxTextExtractionTaskReasonTest {

    private static final String SOME_REASON_DETAIL = "someReasonDetail";

    @Test
    public void shouldBuildWithReasonQuality() {
        SandboxTextExtractionTaskReason result = SandboxTextExtractionTaskReason.builder()
                .forQuality()
                .build();

        assertThat(result.getValue(), is("QUALITY"));
    }

    @Test
    public void shouldBuildWithReasonUserError() {
        SandboxTextExtractionTaskReason result = SandboxTextExtractionTaskReason.builder()
                .forUserError()
                .build();

        assertThat(result.getValue(), is("USER_ERROR"));
    }

    @Test
    public void shouldBuildWithAnyReasonDetail() {
        SandboxTextExtractionTaskReason result = SandboxTextExtractionTaskReason.builder()
                .withDetail(SOME_REASON_DETAIL)
                .build();

        assertThat(result.getDetail(), is(SOME_REASON_DETAIL));
    }

}
