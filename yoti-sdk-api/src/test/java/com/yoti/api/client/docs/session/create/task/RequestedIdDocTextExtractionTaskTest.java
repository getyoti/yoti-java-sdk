package com.yoti.api.client.docs.session.create.task;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class RequestedIdDocTextExtractionTaskTest {

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackAlways() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result, is(instanceOf(RequestedIdDocTextExtractionTask.class)));
        assertThat(result.getConfig(), instanceOf(RequestedIdDocTextExtractionTaskConfig.class));
        assertThat(result.getType(), is("ID_DOCUMENT_TEXT_DATA_EXTRACTION"));

        RequestedIdDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackFallback() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withManualCheckFallback()
                .build();

        RequestedIdDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackNever() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withManualCheckNever()
                .build();

        RequestedIdDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("NEVER"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithChipDataDesired() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withChipDataDesired()
                .build();

        RequestedIdDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getChipData(), is("DESIRED"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithChipDataIgnore() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withChipDataIgnore()
                .build();

        RequestedIdDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getChipData(), is("IGNORE"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithCreateExpandedDocumentFields() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withCreateExpandedDocumentFields(true)
                .build();

        RequestedIdDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getCreateExpandedDocumentFields(), is(true));
    }

}
