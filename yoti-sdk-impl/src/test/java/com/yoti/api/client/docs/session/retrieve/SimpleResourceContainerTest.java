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
public class SimpleResourceContainerTest {

    @Mock ZoomLivenessResourceResponse zoomLivenessResourceResponseMock;
    @Mock LivenessResourceResponse livenessResourceResponse;

    SimpleResourceContainer simpleResourceContainer;

    @Test
    public void shouldFilterZoomLivenessResources() throws NoSuchFieldException {
        simpleResourceContainer = new SimpleResourceContainer();

        FieldSetter.setField(
                simpleResourceContainer,
                simpleResourceContainer.getClass().getDeclaredField("livenessCapture"),
                Arrays.asList(
                        zoomLivenessResourceResponseMock,
                        livenessResourceResponse,
                        livenessResourceResponse
                )
        );

        List<ZoomLivenessResourceResponse> result = simpleResourceContainer.getZoomLivenessResources();
        assertThat(simpleResourceContainer.getLivenessCapture(), hasSize(3));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldReturnEmptyList() throws NoSuchFieldException {
        simpleResourceContainer = new SimpleResourceContainer();

        FieldSetter.setField(
                simpleResourceContainer,
                simpleResourceContainer.getClass().getDeclaredField("livenessCapture"),
                new ArrayList<>()
        );

        List<ZoomLivenessResourceResponse> result = simpleResourceContainer.getZoomLivenessResources();
        assertThat(simpleResourceContainer.getLivenessCapture(), hasSize(0));
        assertThat(result, hasSize(0));
    }

}
