package com.yoti.api.client.docs.session.create.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleRequestedSupplementaryDocTextExtractionTaskBuilderTest {

    @Test
    public void shouldBuildSimpleRequestedSupplementaryTextExtractionTaskWithManualFallbackAlways() {
        RequestedSupplementaryDocTextExtractionTask<?> result = new SimpleRequestedSupplementaryDocTextExtractionTaskBuilder()
                .withManualCheckAlways()
                .build();

        assertThat(result, is(instanceOf(SimpleRequestedSupplementaryDocTextExtractionTask.class)));
        assertThat(result.getConfig(), instanceOf(SimpleRequestedSupplementaryDocTextExtractionTaskConfig.class));
        assertThat(result.getType(), is("SUPPLEMENTARY_DOCUMENT_TEXT_DATA_EXTRACTION"));

        SimpleRequestedSupplementaryDocTextExtractionTaskConfig configResult = (SimpleRequestedSupplementaryDocTextExtractionTaskConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildSimpleRequestedSupplementaryTextExtractionTaskWithManualFallbackFallback() {
        RequestedSupplementaryDocTextExtractionTask<?> result = new SimpleRequestedSupplementaryDocTextExtractionTaskBuilder()
                .withManualCheckFallback()
                .build();

        SimpleRequestedSupplementaryDocTextExtractionTaskConfig configResult = (SimpleRequestedSupplementaryDocTextExtractionTaskConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildSimpleRequestedSupplementaryTextExtractionTaskWithManualFallbackNever() {
        RequestedSupplementaryDocTextExtractionTask<?> result = new SimpleRequestedSupplementaryDocTextExtractionTaskBuilder()
                .withManualCheckNever()
                .build();

        SimpleRequestedSupplementaryDocTextExtractionTaskConfig configResult = (SimpleRequestedSupplementaryDocTextExtractionTaskConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("NEVER"));
    }

}