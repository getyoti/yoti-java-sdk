package com.yoti.api.client.docs.session.retrieve;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.Arrays;
import java.util.List;

import com.yoti.api.client.spi.remote.util.FieldSetter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetSessionResultCheckTest {

    GetSessionResult testObj = new GetSessionResult();

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

    @Test
    public void shouldFilterChecks() {
        List<CheckResponse> allChecks = Arrays.asList(
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
        );
        FieldSetter.setField(testObj, "checks", allChecks);

        assertThat(testObj.getAuthenticityChecks(), contains(authenticityCheckResponse));
        assertThat(testObj.getLivenessChecks(), contains(livenessCheckResponse));
        assertThat(testObj.getIdDocumentTextDataChecks(), contains(textDataCheckResponse));
        assertThat(testObj.getSupplementaryDocumentTextDataChecks(), contains(supplementaryDocumentTextDataCheckResponse));
        assertThat(testObj.getFaceMatchChecks(), contains(faceMatchCheckResponse));
        assertThat(testObj.getFaceComparisonChecks(), contains(faceComparisonCheckResponse));
        assertThat(testObj.getIdDocumentComparisonChecks(), contains(idDocumentComparisonCheckResponse));
        assertThat(testObj.getThirdPartyIdentityChecks(), contains(thirdPartyIdentityCheckResponse));
        assertThat(testObj.getWatchlistScreeningChecks(), contains(watchlistScreeningCheckResponse));
        assertThat(testObj.getIbvVisualReviewChecks(), contains(ibvVisualReviewCheckResponse));
        assertThat(testObj.getDocumentSchemeValidityChecks(), contains(documentSchemeValidityCheckResponse));
        assertThat(testObj.getProfileDocumentMatchChecks(), contains(profileDocumentMatchCheckResponse));
        assertThat(testObj.getThirdPartyIdentityFraudOneChecks(), contains(thirdPartyIdentityFraudOneCheckResponse));
        assertThat(testObj.getSynecticsIdentityFraudChecks(), contains(synecticsIdentityFraudCheckResponse));
    }

}
