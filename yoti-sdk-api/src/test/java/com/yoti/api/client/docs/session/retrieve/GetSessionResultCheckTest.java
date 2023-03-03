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

    GetSessionResult getSessionResult;

    @Test
    public void shouldFilterAuthenticityChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<AuthenticityCheckResponse> result = getSessionResult.getAuthenticityChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterLivenessChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<LivenessCheckResponse> result = getSessionResult.getLivenessChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterTextDataChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<TextDataCheckResponse> result = getSessionResult.getIdDocumentTextDataChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterSupplementaryDocumentTextDataChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<SupplementaryDocumentTextDataCheckResponse> result = getSessionResult.getSupplementaryDocumentTextDataChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterFaceMatchChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<FaceMatchCheckResponse> result = getSessionResult.getFaceMatchChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterFaceComparisonChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<FaceComparisonCheckResponse> result = getSessionResult.getFaceComparisonChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterIdDocumentComparisonChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<IdDocumentComparisonCheckResponse> result = getSessionResult.getIdDocumentComparisonChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterThirdPartyIdentityChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<ThirdPartyIdentityCheckResponse> result = getSessionResult.getThirdPartyIdentityChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterWatchlistScreeningChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<WatchlistScreeningCheckResponse> result = getSessionResult.getWatchlistScreeningChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterIbvVisualReviewChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<IbvVisualReviewCheckResponse> result = getSessionResult.getIbvVisualReviewChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterDocumentSchemeValidityChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<DocumentSchemeValidityCheckResponse> result = getSessionResult.getDocumentSchemeValidityChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterProfileDocumentMatchChecks() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<ProfileDocumentMatchCheckResponse> result = getSessionResult.getProfileDocumentMatchChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterThirdPartyIdentityFraudOneCheck() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<ThirdPartyIdentityFraudOneCheckResponse> result = getSessionResult.getThirdPartyIdentityFraudOneChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterSynecticsIdentityFraudCheck() {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<SynecticsIdentityFraudCheckResponse> result = getSessionResult.getSynecticsIdentityFraudChecks();
        assertThat(getSessionResult.getChecks(), hasSize(SESSION_CHECKS));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldReturnEmptyLists() {
        getSessionResult = new GetSessionResult();

        FieldSetter.setField(
                getSessionResult,
                "checks",
                new ArrayList<>()
        );

        assertThat(getSessionResult.getChecks(), hasSize(0));
        assertThat(getSessionResult.getAuthenticityChecks(), hasSize(0));
        assertThat(getSessionResult.getFaceMatchChecks(), hasSize(0));
        assertThat(getSessionResult.getIdDocumentTextDataChecks(), hasSize(0));
        assertThat(getSessionResult.getSupplementaryDocumentTextDataChecks(), hasSize(0));
        assertThat(getSessionResult.getLivenessChecks(), hasSize(0));
        assertThat(getSessionResult.getThirdPartyIdentityFraudOneChecks(), hasSize(0));
        assertThat(getSessionResult.getSynecticsIdentityFraudChecks(), hasSize(0));
    }

    private void setupGetSessionResult() {
        FieldSetter.setField(
                getSessionResult,
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
