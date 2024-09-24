package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yoti.api.client.spi.remote.util.FieldSetter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetSessionResultCheckTest {

    private static final int SESSION_CHECKS = 14;

    @Mock AuthenticityCheckResponse authenticityCheckResponse;
    @Mock FaceMatchCheckResponse faceMatchCheckResponse;
    @Mock FaceComparisonCheckResponse faceComparisonCheckResponse;
    @Mock TextDataCheckResponse textDataCheckResponse;
    @Mock SupplementaryDocumentTextDataCheckResponse supplementaryDocumentTextDataCheckResponse;
    @Mock LivenessCheckResponse livenessCheckResponse;
    @Mock IdDocumentComparisonCheckResponse idDocumentComparisonCheckResponse;
    @Mock ThirdPartyIdentityCheckResponse thirdPartyIdentityCheckResponse;
    @Mock WatchlistScreeningCheckResponse watchlistScreeningCheckResponse;
    @Mock ThirdPartyIdentityFraudOneCheckResponse thirdPartyIdentityFraudOneCheckResponse;
    @Mock IbvVisualReviewCheckResponse ibvVisualReviewCheckResponse;
    @Mock DocumentSchemeValidityCheckResponse documentSchemeValidityCheckResponse;
    @Mock ProfileDocumentMatchCheckResponse profileDocumentMatchCheckResponse;
    @Mock SynecticsIdentityFraudCheckResponse synecticsIdentityFraudCheckResponse;

    GetSessionResult testObj;

    @Before
    public void setUp() throws Exception {
        testObj = new GetSessionResult();
        setupGetSessionResult();
    }

    @Test
    public void shouldFilterAuthenticityChecks() {
        List<AuthenticityCheckResponse> result = testObj.getAuthenticityChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterLivenessChecks() {
        List<LivenessCheckResponse> result = testObj.getLivenessChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterTextDataChecks() {
        List<TextDataCheckResponse> result = testObj.getIdDocumentTextDataChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterSupplementaryDocumentTextDataChecks() {
        List<SupplementaryDocumentTextDataCheckResponse> result = testObj.getSupplementaryDocumentTextDataChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterFaceMatchChecks() {
        List<FaceMatchCheckResponse> result = testObj.getFaceMatchChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterFaceComparisonChecks() {
        List<FaceComparisonCheckResponse> result = testObj.getFaceComparisonChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterIdDocumentComparisonChecks() {
        List<IdDocumentComparisonCheckResponse> result = testObj.getIdDocumentComparisonChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterThirdPartyIdentityChecks() {
        List<ThirdPartyIdentityCheckResponse> result = testObj.getThirdPartyIdentityChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterWatchlistScreeningChecks() {
        List<WatchlistScreeningCheckResponse> result = testObj.getWatchlistScreeningChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterIbvVisualReviewChecks() {
        List<IbvVisualReviewCheckResponse> result = testObj.getIbvVisualReviewChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterDocumentSchemeValidityChecks() {
        List<DocumentSchemeValidityCheckResponse> result = testObj.getDocumentSchemeValidityChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterProfileDocumentMatchChecks() {
        List<ProfileDocumentMatchCheckResponse> result = testObj.getProfileDocumentMatchChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterThirdPartyIdentityFraudOneCheck() {
        List<ThirdPartyIdentityFraudOneCheckResponse> result = testObj.getThirdPartyIdentityFraudOneChecks();

        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterSynecticsIdentityFraudCheck() {
        List<SynecticsIdentityFraudCheckResponse> result = testObj.getSynecticsIdentityFraudChecks();

        assertThat(result, hasSize(1));
    }

    private void setupGetSessionResult() {
        FieldSetter.setField(
                testObj,
                "checks",
                Arrays.asList(
                        authenticityCheckResponse,
                        livenessCheckResponse,
                        textDataCheckResponse,
                        supplementaryDocumentTextDataCheckResponse,
                        faceMatchCheckResponse,
                        faceComparisonCheckResponse,
                        idDocumentComparisonCheckResponse,
                        thirdPartyIdentityCheckResponse,
                        watchlistScreeningCheckResponse,
                        thirdPartyIdentityFraudOneCheckResponse,
                        ibvVisualReviewCheckResponse,
                        documentSchemeValidityCheckResponse,
                        profileDocumentMatchCheckResponse,
                        synecticsIdentityFraudCheckResponse
                )
        );
    }

}
