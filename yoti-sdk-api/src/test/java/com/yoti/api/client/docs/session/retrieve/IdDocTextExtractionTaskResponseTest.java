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
public class IdDocTextExtractionTaskResponseTest {

    @Mock GeneratedTextDataCheckResponse textDataCheckResponseMock;
    @Mock GeneratedCheckResponse checkResponse;

    IdDocTextExtractionTaskResponse testObj;

    @Test
    public void shouldFilterGeneratedTextDataChecks() throws NoSuchFieldException {
        testObj = new IdDocTextExtractionTaskResponse();

        FieldSetter.setField(
                testObj,
                testObj.getClass().getSuperclass().getDeclaredField("generatedChecks"),
                Arrays.asList(
                        textDataCheckResponseMock,
                        checkResponse,
                        checkResponse
                )
        );

        List<GeneratedTextDataCheckResponse> result = testObj.getGeneratedTextDataChecks();
        assertThat(testObj.getGeneratedChecks(), hasSize(3));
        assertThat(result, hasSize(1));
    }

}
