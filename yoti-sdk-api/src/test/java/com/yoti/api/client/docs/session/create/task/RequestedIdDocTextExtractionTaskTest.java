package com.yoti.api.client.docs.session.create.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class RequestedIdDocTextExtractionTaskTest {

    @Test
    public void shouldDefaultConfigValuesToNull() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder().build();

        assertThat(result.getType(), is("ID_DOCUMENT_TEXT_DATA_EXTRACTION"));
        assertThat(result.getConfig().getChipData(), is(nullValue()));
        assertThat(result.getConfig().getManualCheck(), is(nullValue()));
        assertThat(result.getConfig().getCreateExpandedDocumentFields(), is(nullValue()));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackAlways() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withManualCheckAlways()
                .build();

        assertThat(result.getType(), is("ID_DOCUMENT_TEXT_DATA_EXTRACTION"));
        RequestedIdDocTextExtractionTaskConfig configResult = result.getConfig();
        assertThat(configResult.getManualCheck(), is("ALWAYS"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackFallback() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withManualCheckFallback()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("FALLBACK"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithManualFallbackNever() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withManualCheckNever()
                .build();

        assertThat(result.getConfig().getManualCheck(), is("NEVER"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithChipDataDesired() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withChipDataDesired()
                .build();

        assertThat(result.getConfig().getChipData(), is("DESIRED"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithChipDataIgnore() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withChipDataIgnore()
                .build();

        assertThat(result.getConfig().getChipData(), is("IGNORE"));
    }

    @Test
    public void shouldBuildSimpleRequestedTextExtractionTaskWithCreateExpandedDocumentFields() {
        RequestedIdDocTextExtractionTask result = RequestedIdDocTextExtractionTask.builder()
                .withCreateExpandedDocumentFields(true)
                .build();

        assertThat(result.getConfig().getCreateExpandedDocumentFields(), is(true));
    }

}
