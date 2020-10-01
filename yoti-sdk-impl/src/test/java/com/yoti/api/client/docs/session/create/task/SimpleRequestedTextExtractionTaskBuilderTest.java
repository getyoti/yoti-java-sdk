package com.yoti.api.client.docs.session.create.task;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class SimpleRequestedTextExtractionTaskBuilderTest {

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackAlways() {
        RequestedTextExtractionTask result = new SimpleRequestedTextExtractionTaskBuilder()
                .withManualCheckAlways()
                .build();

        assertThat(result, is(instanceOf(SimpleRequestedTextExtractionTask.class)));
        assertThat(result.getConfig(), instanceOf(SimpleRequestedTextExtractionTaskConfig.class));
        assertThat(result.getType(), is("ID_DOCUMENT_TEXT_DATA_EXTRACTION"));

        SimpleRequestedTextExtractionTaskConfig configResult = (SimpleRequestedTextExtractionTaskConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackFallback() {
        RequestedTextExtractionTask result = new SimpleRequestedTextExtractionTaskBuilder()
                .withManualCheckFallback()
                .build();

        SimpleRequestedTextExtractionTaskConfig configResult = (SimpleRequestedTextExtractionTaskConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackNever() {
        RequestedTextExtractionTask result = new SimpleRequestedTextExtractionTaskBuilder()
                .withManualCheckNever()
                .build();

        SimpleRequestedTextExtractionTaskConfig configResult = (SimpleRequestedTextExtractionTaskConfig) result.getConfig();
        assertThat(configResult.getManualCheck(), is("NEVER"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithChipDataDesired() {
        RequestedTextExtractionTask result = new SimpleRequestedTextExtractionTaskBuilder()
                .withChipDataDesired()
                .build();

        SimpleRequestedTextExtractionTaskConfig configResult = (SimpleRequestedTextExtractionTaskConfig) result.getConfig();
        assertThat(configResult.getChipData(), is("DESIRED"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithChipDataIgnore() {
        RequestedTextExtractionTask result = new SimpleRequestedTextExtractionTaskBuilder()
                .withChipDataIgnore()
                .build();

        SimpleRequestedTextExtractionTaskConfig configResult = (SimpleRequestedTextExtractionTaskConfig) result.getConfig();
        assertThat(configResult.getChipData(), is("IGNORE"));
    }

}
