package com.yoti.api.client.sandbox.docs.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import com.yoti.api.client.sandbox.docs.request.SandboxTaskResults;
import com.yoti.api.client.sandbox.docs.request.task.SandboxTextDataExtractionTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxTaskResultsTest {

    @Mock SandboxTextDataExtractionTask textDataExtractionTaskMock;

    @Test
    public void builder_shouldAllowAddingOfTextDataExtractionTasks() {
        SandboxTaskResults result = SandboxTaskResults.builder()
                .withTextDataExtractionTask(textDataExtractionTaskMock)
                .build();

        assertThat(result.getTextDataExtractionTasks(), containsInAnyOrder(textDataExtractionTaskMock));
    }

}
