package com.yoti.api.client.sandbox.docs.request.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxBreakdown;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxRecommendation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxZoomLivenessCheckTest {

    @Mock SandboxRecommendation sandboxRecommendationMock;
    @Mock SandboxBreakdown sandboxBreakdownMock;

    @Test
    public void builder_shouldThrowExceptionForMissingRecommendation() {
        try {
            SandboxLivenessCheck.forZoomLiveness().build();
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), containsString("recommendation"));
            return;
        }
        fail("Expected an exception");
    }

    @Test
    public void builder_shouldBuildWithCorrectValues() {
        SandboxLivenessCheck result = new SandboxZoomLivenessCheckBuilder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdown(sandboxBreakdownMock)
                .build();

        assertThat(result.getResult().getReport().getRecommendation(), is(sandboxRecommendationMock));
        assertThat(result.getResult().getReport().getBreakdown(), hasSize(1));
        assertThat(result.getResult().getReport().getBreakdown(), containsInAnyOrder(sandboxBreakdownMock));
    }

    @Test
    public void builder_shouldSetCorrectLivenessTypeForZoom() {
        SandboxLivenessCheck result = new SandboxZoomLivenessCheckBuilder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdown(sandboxBreakdownMock)
                .build();

        assertThat(result.getLivenessType(), is("ZOOM"));
    }

    @Test
    public void builder_shouldSetCorrectLivenessTypeForStatic() {
        SandboxLivenessCheck result = new SandboxStaticLivenessCheckBuilder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdown(sandboxBreakdownMock)
                .build();

        assertThat(result.getLivenessType(), is("STATIC"));
    }

}
