package com.yoti.api.client.docs.session.create.task;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class RequestedTextExtractionTaskTest {

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackAlways() {
        RequestedTextExtractionTask result = RequestedTextExtractionTask.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result, is(instanceOf(RequestedTextExtractionTask.class)));
        assertThat(result.getConfig(), instanceOf(RequestedTextExtractionTaskConfig.class));
        assertThat(result.getType(), is("ID_DOCUMENT_TEXT_DATA_EXTRACTION"));

        RequestedTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackFallback() {
        RequestedTextExtractionTask result = RequestedTextExtractionTask.builder()
                .withManualCheckFallback()
                .build();

        RequestedTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackNever() {
        RequestedTextExtractionTask result = RequestedTextExtractionTask.builder()
                .withManualCheckNever()
                .build();

        RequestedTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("NEVER"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithChipDataDesired() {
        RequestedTextExtractionTask result = RequestedTextExtractionTask.builder()
                .withChipDataDesired()
                .build();

        RequestedTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getChipData(), is("DESIRED"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithChipDataIgnore() {
        RequestedTextExtractionTask result = RequestedTextExtractionTask.builder()
                .withChipDataIgnore()
                .build();

        RequestedTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getChipData(), is("IGNORE"));
    }

}
