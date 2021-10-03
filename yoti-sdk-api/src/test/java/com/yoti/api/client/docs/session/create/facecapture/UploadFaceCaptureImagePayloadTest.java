package com.yoti.api.client.docs.session.create.facecapture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.*;

public class UploadFaceCaptureImagePayloadTest {

    private static final byte[] SOME_IMAGE_CONTENTS = new byte[]{ 1, 2, 3, 4 };

    @Test
    public void build_shouldCorrectlyBuildUploadFaceCaptureImagePayloadWithJpegContentType() {
        UploadFaceCaptureImagePayload result = UploadFaceCaptureImagePayload.builder()
                .withImageContents(SOME_IMAGE_CONTENTS)
                .forJpegImage()
                .build();
        assertThat(result.getImageContents(), is(SOME_IMAGE_CONTENTS));
        assertThat(result.getImageContentType(), is("image/jpeg"));
    }

    @Test
    public void build_shouldCorrectlyBuildUploadFaceCaptureImagePayloadWithPngContentType() {
        UploadFaceCaptureImagePayload result = UploadFaceCaptureImagePayload.builder()
                .withImageContents(SOME_IMAGE_CONTENTS)
                .forPngImage()
                .build();
        assertThat(result.getImageContents(), is(SOME_IMAGE_CONTENTS));
        assertThat(result.getImageContentType(), is("image/png"));
    }

}
