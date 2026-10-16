package com.yoti.api.client.docs.session.create.pdf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class CreateSessionOverviewPdfPayloadTest {

    @Test
    public void build_shouldBuildCreateSessionOverviewPdfPayloadCorrectly() {
        CreateSessionOverviewPdfPayload result = CreateSessionOverviewPdfPayload.builder()
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
        CreateSessionOverviewPdfPayload result = CreateSessionOverviewPdfPayload.builder().build();

        assertThat(result.getIncludeImages(), is(nullValue()));
        assertThat(result.getIncludeExtractedText(), is(nullValue()));
        assertThat(result.getIncludeDeviceTracking(), is(nullValue()));
    }

    @Test
    public void shouldOnlySerializeProvidedValues() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        CreateSessionOverviewPdfPayload payload = CreateSessionOverviewPdfPayload.builder()
                .withIncludeExtractedText(false)
                .build();

        assertThat(objectMapper.writeValueAsString(payload), is("{\"include_extracted_text\":false}"));
    }

}
