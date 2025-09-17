package com.yoti.api.client.sandbox.docs.request.check;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import com.yoti.api.client.sandbox.docs.request.check.report.SandboxBreakdown;
import com.yoti.api.client.sandbox.docs.request.check.report.SandboxRecommendation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxSupplementaryDocumentTextDataCheckTest {

    private static final String SOME_KEY = "someKey";
    private static final String SOME_VALUE = "someValue";
    private static final Map<String, Object> SOME_DOCUMENT_FIELDS;

    static {
        SOME_DOCUMENT_FIELDS = new HashMap<>();
        SOME_DOCUMENT_FIELDS.put("firstKey", "firstValue");
        SOME_DOCUMENT_FIELDS.put("secondKey", 100);
    }

    @Mock SandboxRecommendation sandboxRecommendationMock;
    @Mock SandboxBreakdown sandboxBreakdownMock;

    @Test
    public void builder_shouldInitialiseDocumentFieldsMap() {
        SandboxSupplementaryDocumentTextDataCheck result = SandboxSupplementaryDocumentTextDataCheck.builder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdown(sandboxBreakdownMock)
                .withDocumentField(SOME_KEY, SOME_VALUE)
                .build();

        assertThat(result.getResult().getDocumentFields(), hasKey(SOME_KEY));
        assertThat(result.getResult().getDocumentFields(), hasEntry(SOME_KEY, (Object) SOME_VALUE));
    }

    @Test
    public void builder_shouldAllowMapForDocumentFields() {
        SandboxSupplementaryDocumentTextDataCheck result = SandboxSupplementaryDocumentTextDataCheck.builder()
                .withRecommendation(sandboxRecommendationMock)
                .withBreakdown(sandboxBreakdownMock)
                .withDocumentFields(SOME_DOCUMENT_FIELDS)
                .build();

        assertThat(result.getResult().getDocumentFields(), hasEntry("firstKey", (Object) "firstValue"));
        assertThat(result.getResult().getDocumentFields(), hasEntry("secondKey", (Object) 100));
    }
}
