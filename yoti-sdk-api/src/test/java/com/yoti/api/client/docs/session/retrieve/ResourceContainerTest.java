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
public class ResourceContainerTest {

    @Mock ZoomLivenessResourceResponse zoomLivenessResourceResponseMock;
    @Mock LivenessResourceResponse livenessResourceResponse;

    ResourceContainer simpleResourceContainer;

    @Test
    public void shouldFilterZoomLivenessResources() {
        simpleResourceContainer = new ResourceContainer();

        FieldSetter.setField(
                simpleResourceContainer,
                "livenessCapture",
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
    public void shouldReturnEmptyList() {
        simpleResourceContainer = new ResourceContainer();

        FieldSetter.setField(
                simpleResourceContainer,
                "livenessCapture",
                new ArrayList<>()
        );

        List<ZoomLivenessResourceResponse> result = simpleResourceContainer.getZoomLivenessResources();
        assertThat(simpleResourceContainer.getLivenessCapture(), hasSize(0));
        assertThat(result, hasSize(0));
    }

}
