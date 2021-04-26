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
public class GetSessionResultTest {

    @Mock AuthenticityCheckResponse authenticityCheckResponseMock;
    @Mock FaceMatchCheckResponse faceMatchCheckResponseMock;
    @Mock TextDataCheckResponse textDataCheckResponseMock;
    @Mock SupplementaryDocumentTextDataCheckResponse supplementaryDocumentTextDataCheckResponseMock;
    @Mock LivenessCheckResponse livenessCheckResponseMock;
    @Mock IdDocumentComparisonCheckResponse idDocumentComparisonCheckResponse;
    @Mock ThirdPartyIdentityCheckResponse thirdPartyIdentityCheckResponse;
    @Mock WatchlistScreeningCheckResponse watchlistScreeningCheckResponse;

    GetSessionResult getSessionResult;

    @Test
    public void shouldFilterAuthenticityChecks() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<AuthenticityCheckResponse> result = getSessionResult.getAuthenticityChecks();
        assertThat(getSessionResult.getChecks(), hasSize(8));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterLivenessChecks() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<LivenessCheckResponse> result = getSessionResult.getLivenessChecks();
        assertThat(getSessionResult.getChecks(), hasSize(8));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterTextDataChecks() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<TextDataCheckResponse> result = getSessionResult.getIdDocumentTextDataChecks();
        assertThat(getSessionResult.getChecks(), hasSize(8));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterSupplementaryDocumentTextDataChecks() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<SupplementaryDocumentTextDataCheckResponse> result = getSessionResult.getSupplementaryDocumentTextDataChecks();
        assertThat(getSessionResult.getChecks(), hasSize(8));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterFaceMatchChecks() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<FaceMatchCheckResponse> result = getSessionResult.getFaceMatchChecks();
        assertThat(getSessionResult.getChecks(), hasSize(8));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterIdDocumentComparisonChecks() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<IdDocumentComparisonCheckResponse> result = getSessionResult.getIdDocumentComparisonChecks();
        assertThat(getSessionResult.getChecks(), hasSize(8));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterThirdPartyIdentityChecks() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<ThirdPartyIdentityCheckResponse> result = getSessionResult.getThirdPartyIdentityChecks();
        assertThat(getSessionResult.getChecks(), hasSize(8));
        assertThat(result, hasSize(1));
    }

    @Test
    public void shouldFilterWatchlistScreeningChecks() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        setupGetSessionResult();

        List<WatchlistScreeningCheckResponse> result = getSessionResult.getWatchlistScreeningChecks();
        assertThat(getSessionResult.getChecks(), hasSize(8));
        assertThat(result, hasSize(1));

//        WatchlistScreeningCheckResponse watchlistScreeningCheckResponse = result.get(0);
//        WatchlistScreeningReportResponse report = watchlistScreeningCheckResponse.getReport();
//        WatchlistScreeningSummaryResponse watchlistSummary = report.getWatchlistSummary();
//        WatchlistScreeningSearchConfigResponse searchConfig = watchlistSummary.getSearchConfig();
//        List<String> categories = searchConfig.getCategories();
//
//
//        List<WatchlistAdvancedYotiAcctCheckResponse> result2 = getSessionResult.getWatchlistYotiAcctChecks();
//        WatchlistAdvancedYotiAcctCheckResponse watchlistAdvancedYotiAcctCheckResponse = result2.get(0);
//        YotiAcctReportResponse report1 = watchlistAdvancedYotiAcctCheckResponse.getReport();
//        YotiAcctSummaryResponse watchlistAdvancedSummary = report1.getWatchlistSummary();
//        WatchlistAdvancedSearchConfigResponse advancedConfig = watchlistAdvancedSummary.getSearchConfig();
//        boolean removeDeceased = advancedConfig.isRemoveDeceased();
//
//        List<WatchlistAdvancedCustomAcctCheckResponse> result3 = getSessionResult.getWatchlistCustomAcctChecks();
//        WatchlistAdvancedCustomAcctCheckResponse watchlistAdvancedCustomAcctCheckResponse = result3.get(0);
//        CustomAcctReportResponse report2 = watchlistAdvancedCustomAcctCheckResponse.getReport();
//        CustomAcctSummaryResponse watchlistSummary1 = report2.getWatchlistSummary();
//        CustomAccountWatchlistSearchConfigResponse searchConfig1 = watchlistSummary1.getSearchConfig();
//        String apiKey = searchConfig1.getApiKey();
    }

    @Test
    public void shouldReturnEmptyLists() throws NoSuchFieldException {
        getSessionResult = new GetSessionResult();

        FieldSetter.setField(
                getSessionResult,
                getSessionResult.getClass().getDeclaredField("checks"),
                new ArrayList<>()
        );

        assertThat(getSessionResult.getChecks(), hasSize(0));
        assertThat(getSessionResult.getAuthenticityChecks(), hasSize(0));
        assertThat(getSessionResult.getFaceMatchChecks(), hasSize(0));
        assertThat(getSessionResult.getIdDocumentTextDataChecks(), hasSize(0));
        assertThat(getSessionResult.getSupplementaryDocumentTextDataChecks(), hasSize(0));
        assertThat(getSessionResult.getLivenessChecks(), hasSize(0));
    }

    private void setupGetSessionResult() throws NoSuchFieldException {
        FieldSetter.setField(
                getSessionResult,
                getSessionResult.getClass().getDeclaredField("checks"),
                Arrays.asList(
                        authenticityCheckResponseMock,
                        livenessCheckResponseMock,
                        textDataCheckResponseMock,
                        supplementaryDocumentTextDataCheckResponseMock,
                        faceMatchCheckResponseMock,
                        idDocumentComparisonCheckResponse,
                        thirdPartyIdentityCheckResponse,
                        watchlistScreeningCheckResponse
                )
        );
    }

}
