package com.yoti.api.client.sandbox.docs.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import com.yoti.api.client.sandbox.docs.request.task.SandboxDocumentTextDataExtractionTask;
import com.yoti.api.client.sandbox.docs.request.task.SandboxSupplementaryDocTextDataExtractionTask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxTaskResultsTest {

    @Mock SandboxDocumentTextDataExtractionTask textDataExtractionTaskMock;
    @Mock SandboxSupplementaryDocTextDataExtractionTask supplementaryDocTextDataExtractionTaskMock;

    @Test
    public void builder_shouldAllowAddingOfTextDataExtractionTasks() {
        SandboxTaskResults result = SandboxTaskResults.builder()
                .withDocumentTextDataExtractionTask(textDataExtractionTaskMock)
                .build();

        assertThat(result.getDocumentTextDataExtractionTasks(), containsInAnyOrder(textDataExtractionTaskMock));
    }

    @Test
    public void builder_shouldAllowAddingOfSupplementaryDocumentTextDataExtractionTasks() {
        SandboxTaskResults result = SandboxTaskResults.builder()
                .withSupplementaryDocTextDataExtractionTask(supplementaryDocTextDataExtractionTaskMock)
                .build();

        assertThat(result.getSupplementaryTextDataExtractionTasks(), containsInAnyOrder(supplementaryDocTextDataExtractionTaskMock));
    }

}
