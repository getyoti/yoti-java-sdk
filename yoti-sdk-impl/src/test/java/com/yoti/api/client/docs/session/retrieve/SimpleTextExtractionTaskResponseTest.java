package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimpleTextExtractionTaskResponseTest {

    @Mock GeneratedTextDataCheckResponse textDataCheckResponseMock;
    @Mock GeneratedCheckResponse checkResponse;

    SimpleTextExtractionTaskResponse textExtractionTaskResponse;

    @Test
    public void shouldFilterGeneratedTextDataChecks() throws NoSuchFieldException {
        textExtractionTaskResponse = new SimpleTextExtractionTaskResponse();

        FieldSetter.setField(
                textExtractionTaskResponse,
                textExtractionTaskResponse.getClass().getSuperclass().getDeclaredField("generatedChecks"),
                Arrays.asList(
                        textDataCheckResponseMock,
                        checkResponse,
                        checkResponse
                )
        );

        List<GeneratedTextDataCheckResponse> result = textExtractionTaskResponse.getGeneratedTextDataChecks();
        assertThat(textExtractionTaskResponse.getGeneratedChecks(), hasSize(3));
        assertThat(result, hasSize(1));
    }

}
