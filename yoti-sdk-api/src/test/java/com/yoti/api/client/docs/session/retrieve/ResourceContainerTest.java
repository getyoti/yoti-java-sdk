package com.yoti.api.client.docs.session.retrieve;

import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.spi.remote.util.FieldSetter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResourceContainerTest {

    private static final String ID_DOC_1_ID = "idDocumentResource1Id";
    private static final String SUPPLEMENTARY_DOC_1_ID = "supplementaryDocResource1Id";
    private static final String ZOOM_RESOURCE_1_ID = "zoomLivenessResource1MockId";
    private static final String FACE_CAPTURE_1_ID = "faceCaptureResource1Id";
    private static final String PROFILE_1_ID = "applicantProfileResource1Id";

    ResourceContainer testObj = new ResourceContainer();

    @Mock IdDocumentResourceResponse idDocResourceResponse1Mock;
    @Mock IdDocumentResourceResponse idDocResourceResponse2Mock;
    @Mock SupplementaryDocumentResourceResponse supplementaryDocResourceResponse1Mock;
    @Mock SupplementaryDocumentResourceResponse supplementaryDocResourceResponse2Mock;
    @Mock ZoomLivenessResourceResponse zoomLivenessResource1Mock;
    @Mock ZoomLivenessResourceResponse zoomLivenessResource2Mock;
    @Mock StaticLivenessResourceResponse staticLivenessResourceMock;
    @Mock LivenessResourceResponse livenessResource;
    @Mock FaceCaptureResourceResponse faceCaptureResourceResponse1Mock;
    @Mock FaceCaptureResourceResponse faceCaptureResourceResponse2Mock;
    @Mock ApplicantProfileResourceResponse applicantProfileResourceResponse1Mock;
    @Mock ApplicantProfileResourceResponse applicantProfileResourceResponse2Mock;

    @Mock CheckResponse checkResponseMock;

    @Before
    public void setUp() throws Exception {
        when(idDocResourceResponse1Mock.getId()).thenReturn(ID_DOC_1_ID);
        when(idDocResourceResponse2Mock.getId()).thenReturn("idDocumentResource2Id");
        when(supplementaryDocResourceResponse1Mock.getId()).thenReturn(SUPPLEMENTARY_DOC_1_ID);
        when(supplementaryDocResourceResponse2Mock.getId()).thenReturn("supplementaryDocResource2Id");
        when(zoomLivenessResource1Mock.getId()).thenReturn(ZOOM_RESOURCE_1_ID);
        when(zoomLivenessResource2Mock.getId()).thenReturn("zoomLivenessResource2MockId");
        when(faceCaptureResourceResponse1Mock.getId()).thenReturn(FACE_CAPTURE_1_ID);
        when(faceCaptureResourceResponse2Mock.getId()).thenReturn("faceCaptureResource2Id");
        when(applicantProfileResourceResponse1Mock.getId()).thenReturn(PROFILE_1_ID);
        when(applicantProfileResourceResponse2Mock.getId()).thenReturn("applicantProfileResource2Id");
    }

    @Test
    public void getZoomLivenessResources_shouldFilterZoomLivenessResources() {
        FieldSetter.setField(testObj, "livenessCapture", asList(zoomLivenessResource1Mock, staticLivenessResourceMock, livenessResource));

        List<ZoomLivenessResourceResponse> result = testObj.getZoomLivenessResources();

        assertThat(result, contains(zoomLivenessResource1Mock));
    }

    @Test
    public void getZoomLivenessResources_shouldReturnEmptyList() {
        FieldSetter.setField(testObj, "livenessCapture", new ArrayList<>());

        List<ZoomLivenessResourceResponse> result = testObj.getZoomLivenessResources();

        assertThat(result, hasSize(0));
    }

    @Test
    public void getStaticLivenessResources_shouldFilterStaticLivenessResources() {
        FieldSetter.setField(testObj, "livenessCapture", asList(zoomLivenessResource1Mock, staticLivenessResourceMock, livenessResource));

        List<StaticLivenessResourceResponse> result = testObj.getStaticLivenessResources();

        assertThat(result, contains(staticLivenessResourceMock));
    }

    @Test
    public void getStaticLivenessResources_shouldReturnEmptyList() {
        FieldSetter.setField(testObj, "livenessCapture", new ArrayList<>());

        List<StaticLivenessResourceResponse> result = testObj.getStaticLivenessResources();

        assertThat(result, hasSize(0));
    }

    @Test
    public void filterForCheck_shouldHandleNullResources() {
        ResourceContainer result = testObj.filterForCheck(checkResponseMock);

        assertThat(result.getIdDocuments(), is(emptyIterable()));
        assertThat(result.getSupplementaryDocuments(), is(emptyIterable()));
        assertThat(result.getLivenessCapture(), is(emptyIterable()));
        assertThat(result.getFaceCapture(), is(emptyIterable()));
        assertThat(result.getApplicantProfiles(), is(emptyIterable()));
    }

    @Test
    public void filterForCheck_shouldReturnEmptyWhenNoMatches() {
        FieldSetter.setField(testObj, "idDocuments", new ArrayList<>());
        FieldSetter.setField(testObj, "supplementaryDocuments", new ArrayList<>());
        FieldSetter.setField(testObj, "livenessCapture", new ArrayList<>());
        FieldSetter.setField(testObj, "faceCapture", new ArrayList<>());
        FieldSetter.setField(testObj, "applicantProfiles", new ArrayList<>());

        ResourceContainer result = testObj.filterForCheck(checkResponseMock);

        assertThat(result.getIdDocuments(), is(emptyIterable()));
        assertThat(result.getSupplementaryDocuments(), is(emptyIterable()));
        assertThat(result.getLivenessCapture(), is(emptyIterable()));
        assertThat(result.getFaceCapture(), is(emptyIterable()));
        assertThat(result.getApplicantProfiles(), is(emptyIterable()));
    }

    @Test
    public void filterForCheck_shouldReturnFilteredCollections() {
        FieldSetter.setField(testObj, "idDocuments", asList(idDocResourceResponse1Mock, idDocResourceResponse2Mock));
        FieldSetter.setField(testObj, "supplementaryDocuments", asList(supplementaryDocResourceResponse1Mock, supplementaryDocResourceResponse2Mock));
        FieldSetter.setField(testObj, "livenessCapture", asList(zoomLivenessResource1Mock, zoomLivenessResource2Mock, staticLivenessResourceMock));
        FieldSetter.setField(testObj, "faceCapture", asList(faceCaptureResourceResponse1Mock, faceCaptureResourceResponse2Mock));
        FieldSetter.setField(testObj, "applicantProfiles", asList(applicantProfileResourceResponse1Mock, applicantProfileResourceResponse2Mock));
        when(checkResponseMock.getResourcesUsed()).thenReturn(asList(ID_DOC_1_ID, SUPPLEMENTARY_DOC_1_ID, ZOOM_RESOURCE_1_ID, FACE_CAPTURE_1_ID, PROFILE_1_ID));

        ResourceContainer result = testObj.filterForCheck(checkResponseMock);

        assertThat(result.getIdDocuments(), contains(idDocResourceResponse1Mock));
        assertThat(result.getSupplementaryDocuments(), contains(supplementaryDocResourceResponse1Mock));
        assertThat(result.getLivenessCapture(), contains(zoomLivenessResource1Mock));
        assertThat(result.getFaceCapture(), contains(faceCaptureResourceResponse1Mock));
        assertThat(result.getApplicantProfiles(), contains(applicantProfileResourceResponse1Mock));
    }

}
