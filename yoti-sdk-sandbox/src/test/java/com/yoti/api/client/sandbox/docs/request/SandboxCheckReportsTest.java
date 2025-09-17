package com.yoti.api.client.sandbox.docs.request;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.yoti.api.client.sandbox.docs.request.check.SandboxDocumentAuthenticityCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxDocumentFaceMatchCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxFaceComparisonCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxIdDocumentComparisonCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxLivenessCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxDocumentTextDataCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxSupplementaryDocumentTextDataCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxSynecticsIdentityFraudCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxThirdPartyIdentityCheck;
import com.yoti.api.client.sandbox.docs.request.check.SandboxThirdPartyIdentityFraudOneCheck;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SandboxCheckReportsTest {

    @Mock SandboxDocumentAuthenticityCheck documentAuthenticityCheckMock;
    @Mock SandboxDocumentFaceMatchCheck faceMatchCheckMock;
    @Mock SandboxDocumentTextDataCheck textDataCheckMock;
    @Mock SandboxLivenessCheck livenessCheckMock;
    @Mock SandboxIdDocumentComparisonCheck idDocumentComparisonCheckMock;
    @Mock SandboxSupplementaryDocumentTextDataCheck supplementaryDocumentTextDataCheckMock;
    @Mock SandboxThirdPartyIdentityCheck thirdPartyIdentityCheckMock;
    @Mock SandboxFaceComparisonCheck faceComparisonCheckMock;
    @Mock SandboxSynecticsIdentityFraudCheck synecticsIdentityFraudCheckMock;
    @Mock SandboxThirdPartyIdentityFraudOneCheck thirdPartyIdentityFraudOneCheckMock;

    @Test
    public void builder_shouldAllowDocumentAuthenticityChecks() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withDocumentAuthenticityCheck(documentAuthenticityCheckMock)
                .build();

        assertThat(result.getDocumentAuthenticityChecks(), hasSize(1));
        assertThat(result.getDocumentAuthenticityChecks().get(0), is(documentAuthenticityCheckMock));
    }

    @Test
    public void builder_shouldAllowFaceMatchChecks() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withDocumentFaceMatchCheck(faceMatchCheckMock)
                .build();

        assertThat(result.getDocumentFaceMatchChecks(), hasSize(1));
        assertThat(result.getDocumentFaceMatchChecks().get(0), is(faceMatchCheckMock));
    }

    @Test
    public void builder_shouldAllowTextDataChecks() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withDocumentTextDataCheck(textDataCheckMock)
                .build();

        assertThat(result.getDocumentTextDataChecks(), hasSize(1));
        assertThat(result.getDocumentTextDataChecks().get(0), is(textDataCheckMock));
    }

    @Test
    public void builder_shouldAllowLivenessChecks() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withLivenessCheck(livenessCheckMock)
                .build();

        assertThat(result.getLivenessChecks(), hasSize(1));
        assertThat(result.getLivenessChecks().get(0), is(livenessCheckMock));
    }

    @Test
    public void builder_shouldAllowIdDocumentComparisonChecks() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withIdDocumentComparisonCheck(idDocumentComparisonCheckMock)
                .build();

        assertThat(result.getIdDocumentComparisonChecks(), hasSize(1));
        assertThat(result.getIdDocumentComparisonChecks().get(0), is(idDocumentComparisonCheckMock));
    }

    @Test
    public void builder_shouldAllowSupplementaryDocumentTextDataChecks() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withSupplementaryDocumentTextDataCheck(supplementaryDocumentTextDataCheckMock)
                .build();

        assertThat(result.getSupplementaryDocumentTextDataChecks(), hasSize(1));
        assertThat(result.getSupplementaryDocumentTextDataChecks().get(0), is(supplementaryDocumentTextDataCheckMock));
    }

    @Test
    public void builder_shouldAllowThirdPartyIdentityChecks() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withThirdPartyIdentityCheck(thirdPartyIdentityCheckMock)
                .build();

        assertThat(result.getThirdPartyIdentityCheck(), is(thirdPartyIdentityCheckMock));
    }

    @Test
    public void builder_shouldAllowFaceComparisonCheck() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withFaceComparisonCheck(faceComparisonCheckMock)
                .build();

        assertThat(result.getFaceComparisonCheck(), is(faceComparisonCheckMock));
    }

    @Test
    public void builder_shouldAllowSynecticsIdentityFraudChecks() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withSynecticsIdentityFraudCheck(synecticsIdentityFraudCheckMock)
                .build();

        assertThat(result.getSynecticsIdentityFraudChecks(), hasSize(1));
        assertThat(result.getSynecticsIdentityFraudChecks().get(0), is(synecticsIdentityFraudCheckMock));
    }

    @Test
    public void builder_shouldAllowThirdPartyIdentityFraudOneCheck() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withThirdPartyIdentityFraudOneCheck(thirdPartyIdentityFraudOneCheckMock)
                .build();

        assertThat(result.getThirdPartyIdentityFraudOneCheck(), is(thirdPartyIdentityFraudOneCheckMock));
    }

    @Test
    public void builder_shouldAllowOverridingOfAsyncReportDelay() {
        SandboxCheckReports result = SandboxCheckReports.builder()
                .withAsyncReportDelay(50)
                .build();

        assertThat(result.getAsyncReportDelay(), is(50));
    }
}
