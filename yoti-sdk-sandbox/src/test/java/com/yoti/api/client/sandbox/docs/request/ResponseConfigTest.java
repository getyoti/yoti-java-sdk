package com.yoti.api.client.sandbox.docs.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResponseConfigTest {

    @Mock SandboxCheckReports checkReportsMock;
    @Mock SandboxTaskResults taskResultsMock;

    @Test
    public void builder_shouldAllowCheckReportsToBeProvided() {
        ResponseConfig result = ResponseConfig.builder()
                .withCheckReports(checkReportsMock)
                .build();

        assertThat(result.getCheckReports(), is(checkReportsMock));
    }

    @Test
    public void builder_shouldAllowTaskResultsToBeProvided() {
        ResponseConfig result = ResponseConfig.builder()
                .withTaskResults(taskResultsMock)
                .build();

        assertThat(result.getTaskResults(), is(taskResultsMock));
    }

}
