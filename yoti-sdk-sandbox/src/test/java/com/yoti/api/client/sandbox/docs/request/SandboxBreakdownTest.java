package com.yoti.api.client.sandbox.docs.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxBreakdown;

import org.junit.Test;

public class SandboxBreakdownTest {
    
    private static final String SOME_SUB_CHECK = "someSubCheck";
    private static final String SOME_RESULT = "someResult";    
    private static final String SOME_DETAIL_NAME = "someDetailName";
    private static final String SOME_DETAIL_VALUE = "someDetailValue";

    @Test
    public void builder_shouldThrowExceptionWhenMissingSubCheck() {
        try {
            SandboxBreakdown.builder().build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("subCheck"));
            return;
        }
        fail("Expected an exception");
    }
    
    @Test
    public void builder_shouldThrowExceptionWhenMissingResult() {
        try {
            SandboxBreakdown.builder()
                    .withSubCheck(SOME_SUB_CHECK)
                    .build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("result"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void builder_shouldBuildCorrectlyWithRequiredProperties() {
        SandboxBreakdown result = SandboxBreakdown.builder()
                .withSubCheck(SOME_SUB_CHECK)
                .withResult(SOME_RESULT)
                .build();

        assertThat(result.getSubCheck(), is(SOME_SUB_CHECK));
        assertThat(result.getResult(), is(SOME_RESULT));
        assertThat(result.getDetails(), hasSize(0));
    }

    @Test
    public void builder_shouldCreateSandboxDetailsForSuppliedKeyValue() {
        SandboxBreakdown result = SandboxBreakdown.builder()
                .withSubCheck(SOME_SUB_CHECK)
                .withResult(SOME_RESULT)
                .withDetail(SOME_DETAIL_NAME, SOME_DETAIL_VALUE)
                .build();

        assertThat(result.getDetails(), hasSize(1));
        assertThat(result.getDetails().get(0).getName(), is(SOME_DETAIL_NAME));
        assertThat(result.getDetails().get(0).getValue(), is(SOME_DETAIL_VALUE));
    }

}
