package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

import java.util.Arrays;
import java.util.List;

import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredIdDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.document.RequiredSupplementaryDocumentResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.facecapture.RequiredFaceCaptureResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredLivenessResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredZoomLivenessResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.UnknownRequiredLivenessResourceResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CaptureResponseTest {

    @Mock RequiredIdDocumentResourceResponse idDocumentResourceResponseMock;
    @Mock RequiredSupplementaryDocumentResourceResponse supplementaryDocumentResourceResponseMock;
    @Mock RequiredZoomLivenessResourceResponse zoomLivenessResourceResponseMock;
    @Mock UnknownRequiredLivenessResourceResponse unknownRequiredLivenessResourceResponseMock;
    @Mock RequiredFaceCaptureResourceResponse faceCaptureResourceResponseMock;

    CaptureResponse captureResponse;

    @Before
    public void setUp() throws Exception {
        captureResponse = new CaptureResponse();
        setupCaptureResponse();
    }

    @Test
    public void getResourceRequirements_shouldReturnAllRequiredResources() {
        List<RequiredResourceResponse> result = captureResponse.getResourceRequirements();

        assertThat(result, hasSize(5));
        assertThat(result, containsInAnyOrder(
                idDocumentResourceResponseMock,
                supplementaryDocumentResourceResponseMock,
                zoomLivenessResourceResponseMock,
                unknownRequiredLivenessResourceResponseMock,
                faceCaptureResourceResponseMock)
        );
    }

    @Test
    public void getDocumentResourceRequirements_shouldReturnAllDocumentResourceRequirements() {
        List<RequiredDocumentResourceResponse> result = captureResponse.getDocumentResourceRequirements();

        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(idDocumentResourceResponseMock, supplementaryDocumentResourceResponseMock));
    }

    @Test
    public void getIdDocumentResourceRequirements_shouldOnlyReturnIdDocumentResourceRequirements() {
        List<RequiredIdDocumentResourceResponse> result = captureResponse.getIdDocumentResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(idDocumentResourceResponseMock));
    }

    @Test
    public void getSupplementaryDocumentResourceRequirements_shouldOnlyReturnSupplementaryDocumentResourceRequirements() {
        List<RequiredSupplementaryDocumentResourceResponse> result = captureResponse.getSupplementaryResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(supplementaryDocumentResourceResponseMock));
    }

    @Test
    public void getLivenessResourceRequirements_shouldReturnAllLivenessResourceRequirements() {
        List<RequiredLivenessResourceResponse> result = captureResponse.getLivenessResourceRequirements();

        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(zoomLivenessResourceResponseMock, unknownRequiredLivenessResourceResponseMock));
    }

    @Test
    public void getZoomLivenessResourceRequirements_shouldReturnOnlyZoomLivenessResourceRequirements() {
        List<RequiredZoomLivenessResourceResponse> result = captureResponse.getZoomLivenessResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(zoomLivenessResourceResponseMock));
    }

    @Test
    public void getFaceCaptureResourceRequirements_shouldReturnFaceCaptureResourceRequirements() {
        List<RequiredFaceCaptureResourceResponse> result = captureResponse.getFaceCaptureResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(faceCaptureResourceResponseMock));
    }

    private void setupCaptureResponse() throws NoSuchFieldException {
        FieldSetter.setField(
                captureResponse,
                captureResponse.getClass().getDeclaredField("requiredResources"),
                Arrays.asList(
                        idDocumentResourceResponseMock,
                        supplementaryDocumentResourceResponseMock,
                        zoomLivenessResourceResponseMock,
                        unknownRequiredLivenessResourceResponseMock,
                        faceCaptureResourceResponseMock
                )
        );
    }

}
