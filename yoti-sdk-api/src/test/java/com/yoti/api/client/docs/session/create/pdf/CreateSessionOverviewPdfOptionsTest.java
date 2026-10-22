package com.yoti.api.client.docs.session.create.pdf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class CreateSessionOverviewPdfOptionsTest {

    @Test
    public void build_shouldBuildCreateSessionOverviewPdfOptionsCorrectly() {
        CreateSessionOverviewPdfOptions result = CreateSessionOverviewPdfOptions.builder()
                .withIncludeImages(true)
                .withIncludeExtractedText(false)
                .withIncludeDeviceTracking(true)
                .build();

        assertThat(result.getIncludeImages(), is(true));
        assertThat(result.getIncludeExtractedText(), is(false));
        assertThat(result.getIncludeDeviceTracking(), is(true));
    }

    @Test
    public void build_shouldLeaveUnsetValuesNull() {
        CreateSessionOverviewPdfOptions result = CreateSessionOverviewPdfOptions.builder().build();

        assertThat(result.getIncludeImages(), is(nullValue()));
        assertThat(result.getIncludeExtractedText(), is(nullValue()));
        assertThat(result.getIncludeDeviceTracking(), is(nullValue()));
    }

    @Test
    public void shouldOnlySerializeProvidedValues() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        CreateSessionOverviewPdfOptions options = CreateSessionOverviewPdfOptions.builder()
                .withIncludeExtractedText(false)
                .build();

        assertThat(objectMapper.writeValueAsString(options), is("{\"include_extracted_text\":false}"));
    }

}
