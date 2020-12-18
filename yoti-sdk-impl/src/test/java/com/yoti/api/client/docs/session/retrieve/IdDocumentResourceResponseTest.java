package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IdDocumentResourceResponseTest {

    @Mock TextExtractionTaskResponse textExtractionTaskResponseMock;
    @Mock TaskResponse taskResponseMock;

    IdDocumentResourceResponse simpleIdDocumentResourceResponse;

    @Test
    public void shouldFilterTextExtractionTasks() throws NoSuchFieldException {
        simpleIdDocumentResourceResponse = new IdDocumentResourceResponse();

        FieldSetter.setField(
                simpleIdDocumentResourceResponse,
                simpleIdDocumentResourceResponse.getClass().getSuperclass().getDeclaredField("tasks"),
                Arrays.asList(
                        textExtractionTaskResponseMock,
                        taskResponseMock
                )
        );

        List<TextExtractionTaskResponse> result = simpleIdDocumentResourceResponse.getTextExtractionTasks();
        assertThat(simpleIdDocumentResourceResponse.getTasks(), hasSize(2));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldReturnEmptyList() throws NoSuchFieldException {
        simpleIdDocumentResourceResponse = new IdDocumentResourceResponse();

        FieldSetter.setField(
                simpleIdDocumentResourceResponse,
                simpleIdDocumentResourceResponse.getClass().getSuperclass().getDeclaredField("tasks"),
                new ArrayList<>()
        );

        List<TextExtractionTaskResponse> result = simpleIdDocumentResourceResponse.getTextExtractionTasks();
        assertThat(simpleIdDocumentResourceResponse.getTasks(), hasSize(0));
        assertThat(result, hasSize(0));
    }

}
