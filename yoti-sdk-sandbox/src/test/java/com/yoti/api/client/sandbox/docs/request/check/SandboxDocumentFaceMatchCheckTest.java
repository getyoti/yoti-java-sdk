package com.yoti.api.client.sandbox.docs.request.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxBreakdown;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxRecommendation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxDocumentFaceMatchCheckTest {

    @Mock SandboxRecommendation sandboxRecommendationMock;
    @Mock SandboxBreakdown sandboxBreakdownMock;

    @Test
    public void builder_shouldBuildCorrectly() {
        SandboxDocumentFaceMatchCheck result = SandboxDocumentFaceMatchCheck.builder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdown(sandboxBreakdownMock)
                .build();

        assertThat(result.getResult().getReport().getRecommendation(), is(sandboxRecommendationMock));
        assertThat(result.getResult().getReport().getBreakdown(), containsInAnyOrder(sandboxBreakdownMock));
        assertThat(result.getDocumentFilter(), is(nullValue()));
    }

}
