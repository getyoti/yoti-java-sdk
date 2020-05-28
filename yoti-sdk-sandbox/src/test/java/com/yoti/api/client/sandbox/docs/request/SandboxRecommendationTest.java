package com.yoti.api.client.sandbox.docs.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxRecommendation;

import org.junit.Test;

public class SandboxRecommendationTest {

    private static final String SOME_VALUE = "someValue";
    private static final String SOME_REASON = "someReason";
    private static final String SOME_RECOVERY_SUGGESTION = "someRecoverySuggestion";
    
    @Test
    public void builder_shouldThrowExceptionIfMissingValueProperty() {
        try {
            SandboxRecommendation.builder().build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("value"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void builder_shouldBuildWithCorrectProperties() {
        SandboxRecommendation result = SandboxRecommendation.builder()
                .withValue(SOME_VALUE)
                .withReason(SOME_REASON)
                .withRecoverySuggestion(SOME_RECOVERY_SUGGESTION)
                .build();

        assertThat(result.getValue(), is(SOME_VALUE));
        assertThat(result.getReason(), is(SOME_REASON));
        assertThat(result.getRecoverySuggestion(), is(SOME_RECOVERY_SUGGESTION));
    }


    
}
