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
        CreateSessionOverviewPdfOptions options = CreateSessionOverviewPdfOptions.builder().build();

        CreateSessionOverviewPdfPayload result = CreateSessionOverviewPdfPayload.builder()
                .withOptions(options)
                .build();

        assertThat(result.getOptions(), is(options));
    }

    @Test
    public void build_shouldLeaveUnsetOptionsNull() {
        CreateSessionOverviewPdfPayload result = CreateSessionOverviewPdfPayload.builder().build();

        assertThat(result.getOptions(), is(nullValue()));
    }

    @Test
    public void shouldSerializeOptionsUnderOptionsProperty() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        CreateSessionOverviewPdfPayload payload = CreateSessionOverviewPdfPayload.builder()
                .withOptions(CreateSessionOverviewPdfOptions.builder()
                        .withIncludeImages(true)
                        .withIncludeExtractedText(false)
                        .withIncludeDeviceTracking(true)
                        .build())
                .build();

        assertThat(objectMapper.writeValueAsString(payload),
                is("{\"options\":{\"include_images\":true,\"include_extracted_text\":false,\"include_device_tracking\":true}}"));
    }

}
