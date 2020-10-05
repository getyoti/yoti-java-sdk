package com.yoti.api.client.sandbox.docs.request.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxTextExtractionTaskRecommendationTest {

    @Mock SandboxTextExtractionTaskReason reasonMock;

    @Test
    public void shouldBuildWithRecommendationProgress() {
        SandboxTextExtractionTaskRecommendation result = SandboxTextExtractionTaskRecommendation.builder()
                .forProgress()
                .build();

        assertThat(result.getValue(), is("PROGRESS"));
    }

    @Test
    public void shouldBuildWithRecommendationShouldTryAgain() {
        SandboxTextExtractionTaskRecommendation result = SandboxTextExtractionTaskRecommendation.builder()
                .forShouldTryAgain()
                .build();

        assertThat(result.getValue(), is("SHOULD_TRY_AGAIN"));
    }

    @Test
    public void shouldBuildWithRecommendationMustTryAgain() {
        SandboxTextExtractionTaskRecommendation result = SandboxTextExtractionTaskRecommendation.builder()
                .forMustTryAgain()
                .build();

        assertThat(result.getValue(), is("MUST_TRY_AGAIN"));
    }

    @Test
    public void shouldBuildWithReason() {
        SandboxTextExtractionTaskRecommendation result = SandboxTextExtractionTaskRecommendation.builder()
                .withReason(reasonMock)
                .build();

        assertThat(result.getReason(), is(reasonMock));
    }

}
