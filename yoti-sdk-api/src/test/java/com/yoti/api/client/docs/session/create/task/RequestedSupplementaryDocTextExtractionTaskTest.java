package com.yoti.api.client.docs.session.create.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class RequestedSupplementaryDocTextExtractionTaskTest {

    @Test
    public void shouldBuildSimpleRequestedSupplementaryTextExtractionTaskWithManualFallbackAlways() {
        RequestedSupplementaryDocTextExtractionTask result = RequestedSupplementaryDocTextExtractionTask.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result, is(instanceOf(RequestedSupplementaryDocTextExtractionTask.class)));
        assertThat(result.getConfig(), instanceOf(RequestedSupplementaryDocTextExtractionTaskConfig.class));
        assertThat(result.getType(), is("SUPPLEMENTARY_DOCUMENT_TEXT_DATA_EXTRACTION"));

        RequestedSupplementaryDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildSimpleRequestedSupplementaryTextExtractionTaskWithManualFallbackFallback() {
        RequestedSupplementaryDocTextExtractionTask result = RequestedSupplementaryDocTextExtractionTask.builder()
                .withManualCheckFallback()
                .build();

        RequestedSupplementaryDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildSimpleRequestedSupplementaryTextExtractionTaskWithManualFallbackNever() {
        RequestedSupplementaryDocTextExtractionTask result = RequestedSupplementaryDocTextExtractionTask.builder()
                .withManualCheckNever()
                .build();

        RequestedSupplementaryDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("NEVER"));
    }

}