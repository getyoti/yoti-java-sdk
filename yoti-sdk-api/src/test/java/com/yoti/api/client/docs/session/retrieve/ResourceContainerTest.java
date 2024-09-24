package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
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

    ResourceContainer testObj = new ResourceContainer();

    @Mock ZoomLivenessResourceResponse zoomLivenessResourceMock;
    @Mock StaticLivenessResourceResponse staticLivenessResourceMock;
    @Mock LivenessResourceResponse livenessResource;

    @Test
    public void shouldFilterZoomLivenessResources() {
        FieldSetter.setField(testObj, "livenessCapture", Arrays.asList(zoomLivenessResourceMock, staticLivenessResourceMock, livenessResource));

        List<ZoomLivenessResourceResponse> result = testObj.getZoomLivenessResources();

        assertThat(result, contains(zoomLivenessResourceMock));
    }

    @Test
    public void shouldFilterStaticLivenessResources() {
        FieldSetter.setField(testObj, "livenessCapture", Arrays.asList(zoomLivenessResourceMock, staticLivenessResourceMock, livenessResource));

        List<StaticLivenessResourceResponse> result = testObj.getStaticLivenessResources();

        assertThat(result, contains(staticLivenessResourceMock));
    }

    @Test
    public void shouldReturnEmptyList() {
        FieldSetter.setField(testObj, "livenessCapture", new ArrayList<>());

        List<ZoomLivenessResourceResponse> result = testObj.getZoomLivenessResources();

        assertThat(result, hasSize(0));
    }

}
