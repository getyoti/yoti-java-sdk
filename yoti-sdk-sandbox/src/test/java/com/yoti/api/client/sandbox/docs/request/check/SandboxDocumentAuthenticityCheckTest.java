package com.yoti.api.client.sandbox.docs.request.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxBreakdown;
import com.yoti.api.client.sandbox.docs.request.SandboxDocumentFilter;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxRecommendation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxDocumentAuthenticityCheckTest {

    @Mock SandboxRecommendation sandboxRecommendationMock;
    @Mock SandboxBreakdown sandboxBreakdownMock;
    @Mock SandboxDocumentFilter sandboxDocumentFilterMock;

    @Test
    public void builder_shouldThrowExceptionWhenMissingRecommendation() {
        try {
            SandboxDocumentAuthenticityCheck.builder().build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("recommendation"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void builder_shouldBuildWithoutDocumentFilter() {
        SandboxDocumentAuthenticityCheck result = SandboxDocumentAuthenticityCheck.builder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdown(sandboxBreakdownMock)
                .build();

        assertThat(result.getResult().getReport().getRecommendation(), is(sandboxRecommendationMock));
        assertThat(result.getResult().getReport().getBreakdown(), containsInAnyOrder(sandboxBreakdownMock));
        assertThat(result.getDocumentFilter(), is(nullValue()));
    }

    @Test
    public void builder_shouldBuildWithDocumentFilter() {
        SandboxDocumentAuthenticityCheck result = SandboxDocumentAuthenticityCheck.builder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdown(sandboxBreakdownMock)
                .withDocumentFilter(sandboxDocumentFilterMock)
                .build();

        assertThat(result.getResult().getReport().getRecommendation(), is(sandboxRecommendationMock));
        assertThat(result.getResult().getReport().getBreakdown(), containsInAnyOrder(sandboxBreakdownMock));
        assertThat(result.getDocumentFilter(), is(sandboxDocumentFilterMock));
    }

    @Test
    public void builder_shouldAllowListOfBreakdowns() {
        List<SandboxBreakdown> breakdownList = Arrays.asList(sandboxBreakdownMock, sandboxBreakdownMock, sandboxBreakdownMock);

        SandboxDocumentAuthenticityCheck result = SandboxDocumentAuthenticityCheck.builder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdownList(breakdownList)
                .build();

        assertThat(result.getResult().getReport().getBreakdown(), hasSize(3));
    }

}
