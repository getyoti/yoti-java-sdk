package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yoti.api.client.spi.remote.util.FieldSetter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IdDocumentResourceResponseTest {

    @Mock IdDocTextExtractionTaskResponse idDocTextExtractionTaskResponseMock;
    @Mock TaskResponse taskResponseMock;

    IdDocumentResourceResponse testObj;

    @Test
    public void shouldFilterTextExtractionTasks() {
        testObj = new IdDocumentResourceResponse();

        FieldSetter.setField(
                testObj,
                "tasks",
                Arrays.asList(
                        idDocTextExtractionTaskResponseMock,
                        taskResponseMock
                )
        );

        List<IdDocTextExtractionTaskResponse> result = testObj.getTextExtractionTasks();
        assertThat(testObj.getTasks(), hasSize(2));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldReturnEmptyList() {
        testObj = new IdDocumentResourceResponse();

        FieldSetter.setField(
                testObj,
                "tasks",
                new ArrayList<>()
        );

        List<IdDocTextExtractionTaskResponse> result = testObj.getTextExtractionTasks();
        assertThat(testObj.getTasks(), hasSize(0));
        assertThat(result, hasSize(0));
    }

}
