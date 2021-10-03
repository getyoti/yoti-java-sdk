package com.yoti.api.client.docs.session.create.facecapture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class CreateFaceCaptureResourcePayloadTest {

    private static final String SOME_REQUIREMENT_ID = "someRequirementId";

    @Test
    public void build_shouldBuildCreateFaceCaptureResourcePayloadCorrectly() {
        CreateFaceCaptureResourcePayload result = CreateFaceCaptureResourcePayload.builder()
                .withRequirementId(SOME_REQUIREMENT_ID)
                .build();

        assertThat(result.getRequirementId(), is(SOME_REQUIREMENT_ID));
    }

}
