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
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredStaticLivenessResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.RequiredZoomLivenessResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.liveness.UnknownRequiredLivenessResourceResponse;
import com.yoti.api.client.spi.remote.util.FieldSetter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CaptureResponseTest {

    @Mock RequiredIdDocumentResourceResponse idDocRequirementMock;
    @Mock RequiredSupplementaryDocumentResourceResponse supplementaryDocRequirementMock;
    @Mock RequiredZoomLivenessResourceResponse zoomRequirementMock;
    @Mock RequiredStaticLivenessResourceResponse staticRequirementMock;
    @Mock UnknownRequiredLivenessResourceResponse unknownLivenessRequirementMock;
    @Mock RequiredFaceCaptureResourceResponse faceCaptureRequirementMock;

    CaptureResponse captureResponse;

    @Before
    public void setUp() {
        final List<RequiredResourceResponse> requirements = Arrays.asList(idDocRequirementMock,
                supplementaryDocRequirementMock,
                zoomRequirementMock,
                staticRequirementMock,
                unknownLivenessRequirementMock,
                faceCaptureRequirementMock);
        captureResponse = new CaptureResponse();
        FieldSetter.setField(captureResponse, "requiredResources", requirements);
    }

    @Test
    public void getResourceRequirements_shouldReturnAllRequiredResources() {
        List<RequiredResourceResponse> result = captureResponse.getResourceRequirements();

        assertThat(result, hasSize(6));
        assertThat(result, containsInAnyOrder(
                idDocRequirementMock,
                supplementaryDocRequirementMock,
                zoomRequirementMock,
                staticRequirementMock,
                unknownLivenessRequirementMock,
                faceCaptureRequirementMock)
        );
    }

    @Test
    public void getDocumentResourceRequirements_shouldReturnAllDocumentResourceRequirements() {
        List<RequiredDocumentResourceResponse> result = captureResponse.getDocumentResourceRequirements();

        assertThat(result, hasSize(2));
        assertThat(result, containsInAnyOrder(idDocRequirementMock, supplementaryDocRequirementMock));
    }

    @Test
    public void getIdDocumentResourceRequirements_shouldOnlyReturnIdDocumentResourceRequirements() {
        List<RequiredIdDocumentResourceResponse> result = captureResponse.getIdDocumentResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(idDocRequirementMock));
    }

    @Test
    public void getSupplementaryDocumentResourceRequirements_shouldOnlyReturnSupplementaryDocumentResourceRequirements() {
        List<RequiredSupplementaryDocumentResourceResponse> result = captureResponse.getSupplementaryResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(supplementaryDocRequirementMock));
    }

    @Test
    public void getLivenessResourceRequirements_shouldReturnAllLivenessResourceRequirements() {
        List<RequiredLivenessResourceResponse> result = captureResponse.getLivenessResourceRequirements();

        assertThat(result, hasSize(3));
        assertThat(result, containsInAnyOrder(zoomRequirementMock, staticRequirementMock, unknownLivenessRequirementMock));
    }

    @Test
    public void getZoomLivenessResourceRequirements_shouldReturnOnlyZoomLivenessResourceRequirements() {
        List<RequiredZoomLivenessResourceResponse> result = captureResponse.getZoomLivenessResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(zoomRequirementMock));
    }

    @Test
    public void getStaticLivenessResourceRequirements_shouldReturnOnlyStaticLivenessResourceRequirements() {
        List<RequiredStaticLivenessResourceResponse> result = captureResponse.getStaticLivenessResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(staticRequirementMock));
    }

    @Test
    public void getFaceCaptureResourceRequirements_shouldReturnFaceCaptureResourceRequirements() {
        List<RequiredFaceCaptureResourceResponse> result = captureResponse.getFaceCaptureResourceRequirements();

        assertThat(result, hasSize(1));
        assertThat(result, containsInAnyOrder(faceCaptureRequirementMock));
    }

}
