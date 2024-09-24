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
public class GetSessionResultTest {

    GetSessionResult testObj = new GetSessionResult();

    @Mock AuthenticityCheckResponse authenticityCheckResponseMock;
    @Mock FaceMatchCheckResponse faceMatchCheckResponseMock;
    @Mock FaceComparisonCheckResponse faceComparisonCheckResponseMock;
    @Mock TextDataCheckResponse textDataCheckResponseMock;
    @Mock SupplementaryDocumentTextDataCheckResponse supplementaryDocumentTextDataCheckResponseMock;
    @Mock LivenessCheckResponse livenessCheckResponseMock;
    @Mock IdDocumentComparisonCheckResponse idDocumentComparisonCheckResponseMock;
    @Mock ThirdPartyIdentityCheckResponse thirdPartyIdentityCheckResponseMock;
    @Mock WatchlistScreeningCheckResponse watchlistScreeningCheckResponseMock;
    @Mock ThirdPartyIdentityFraudOneCheckResponse thirdPartyIdentityFraudOneCheckResponseMock;
    @Mock IbvVisualReviewCheckResponse ibvVisualReviewCheckResponseMock;
    @Mock DocumentSchemeValidityCheckResponse documentSchemeValidityCheckResponseMock;
    @Mock ProfileDocumentMatchCheckResponse profileDocumentMatchCheckResponseMock;
    @Mock SynecticsIdentityFraudCheckResponse synecticsIdentityFraudCheckResponseMock;

    @Test
    public void shouldFilterChecks() {
        List<CheckResponse> allChecks = Arrays.asList(
                authenticityCheckResponseMock,
                livenessCheckResponseMock,
                textDataCheckResponseMock,
                supplementaryDocumentTextDataCheckResponseMock,
                faceMatchCheckResponseMock,
                faceComparisonCheckResponseMock,
                idDocumentComparisonCheckResponseMock,
                thirdPartyIdentityCheckResponseMock,
                watchlistScreeningCheckResponseMock,
                thirdPartyIdentityFraudOneCheckResponseMock,
                ibvVisualReviewCheckResponseMock,
                documentSchemeValidityCheckResponseMock,
                profileDocumentMatchCheckResponseMock,
                synecticsIdentityFraudCheckResponseMock
        );
        FieldSetter.setField(testObj, "checks", allChecks);

        assertThat(testObj.getAuthenticityChecks(), contains(authenticityCheckResponseMock));
        assertThat(testObj.getLivenessChecks(), contains(livenessCheckResponseMock));
        assertThat(testObj.getIdDocumentTextDataChecks(), contains(textDataCheckResponseMock));
        assertThat(testObj.getSupplementaryDocumentTextDataChecks(), contains(supplementaryDocumentTextDataCheckResponseMock));
        assertThat(testObj.getFaceMatchChecks(), contains(faceMatchCheckResponseMock));
        assertThat(testObj.getFaceComparisonChecks(), contains(faceComparisonCheckResponseMock));
        assertThat(testObj.getIdDocumentComparisonChecks(), contains(idDocumentComparisonCheckResponseMock));
        assertThat(testObj.getThirdPartyIdentityChecks(), contains(thirdPartyIdentityCheckResponseMock));
        assertThat(testObj.getWatchlistScreeningChecks(), contains(watchlistScreeningCheckResponseMock));
        assertThat(testObj.getIbvVisualReviewChecks(), contains(ibvVisualReviewCheckResponseMock));
        assertThat(testObj.getDocumentSchemeValidityChecks(), contains(documentSchemeValidityCheckResponseMock));
        assertThat(testObj.getProfileDocumentMatchChecks(), contains(profileDocumentMatchCheckResponseMock));
        assertThat(testObj.getThirdPartyIdentityFraudOneChecks(), contains(thirdPartyIdentityFraudOneCheckResponseMock));
        assertThat(testObj.getSynecticsIdentityFraudChecks(), contains(synecticsIdentityFraudCheckResponseMock));
    }

}
