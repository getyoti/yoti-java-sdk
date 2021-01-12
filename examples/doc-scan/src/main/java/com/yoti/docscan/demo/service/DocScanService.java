package com.yoti.docscan.demo.service;

import java.util.Arrays;

import com.yoti.api.client.Media;
import com.yoti.api.client.docs.DocScanClient;
import com.yoti.api.client.docs.DocScanException;
import com.yoti.api.client.docs.session.create.CreateSessionResult;
import com.yoti.api.client.docs.session.create.SdkConfig;
import com.yoti.api.client.docs.session.create.SessionSpec;
import com.yoti.api.client.docs.session.create.check.RequestedDocumentAuthenticityCheck;
import com.yoti.api.client.docs.session.create.check.RequestedFaceMatchCheck;
import com.yoti.api.client.docs.session.create.check.RequestedIdDocumentComparisonCheck;
import com.yoti.api.client.docs.session.create.check.RequestedLivenessCheck;
import com.yoti.api.client.docs.session.create.check.RequestedThirdPartyIdentityCheck;
import com.yoti.api.client.docs.session.create.filters.OrthogonalRestrictionsFilter;
import com.yoti.api.client.docs.session.create.filters.RequiredIdDocument;
import com.yoti.api.client.docs.session.create.filters.RequiredSupplementaryDocument;
import com.yoti.api.client.docs.session.create.objective.ProofOfAddressObjective;
import com.yoti.api.client.docs.session.create.task.RequestedSupplementaryDocTextExtractionTask;
import com.yoti.api.client.docs.session.create.task.RequestedTextExtractionTask;
import com.yoti.api.client.docs.session.retrieve.GetSessionResult;
import com.yoti.api.client.spi.remote.call.YotiConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocScanService {

    private static final String IFRAME_URL_FORMAT = "%s/web/index.html?sessionID=%s&sessionToken=%s";

    private final DocScanClient docScanClient;

    @Autowired
    public DocScanService(DocScanClient docScanClient) {
        this.docScanClient = docScanClient;
    }

    public CreateSessionResult createSession() throws DocScanException {
        SdkConfig sdkConfig = SdkConfig.builder()
                .withAllowsCameraAndUpload()
                .withPrimaryColour("#2d9fff")
                .withSecondaryColour("#FFFFFF")
                .withFontColour("#FFFFFF")
                .withLocale("en-GB")
                .withPresetIssuingCountry("GBR")
                .withSuccessUrl("https://localhost:8443/success")
                .withErrorUrl("https://localhost:8443/error")
                .withPrivacyPolicyUrl("https://localhost:8443/privacy-policy")
                .build();

        SessionSpec sessionSpec = SessionSpec.builder()
                .withClientSessionTokenTtl(600)
                .withResourcesTtl(90000)
                .withUserTrackingId("some-user-tracking-id")
                .withSdkConfig(sdkConfig)
                .withRequestedCheck(
                        RequestedDocumentAuthenticityCheck.builder()
                                .build()
                )
                .withRequestedCheck(
                        RequestedFaceMatchCheck.builder()
                                .withManualCheckAlways()
                                .build()
                )
                .withRequestedCheck(
                        RequestedLivenessCheck.builder()
                                .forZoomLiveness()
                                .withMaxRetries(1)
                                .build()
                )
                .withRequestedCheck(
                        RequestedIdDocumentComparisonCheck.builder()
                                .build()
                )
                .withRequestedCheck(
                        RequestedThirdPartyIdentityCheck.builder()
                                .build()
                )
                .withRequestedTask(
                        RequestedTextExtractionTask.builder()
                                .withManualCheckAlways()
                                .build()
                )
                .withRequestedTask(
                        RequestedSupplementaryDocTextExtractionTask.builder()
                                .withManualCheckAlways()
                                .build()
                )
                .withRequiredDocument(
                        RequiredIdDocument.builder()
                                .withFilter(
                                        OrthogonalRestrictionsFilter.builder()
                                                .withWhitelistedDocumentTypes(Arrays.asList("PASSPORT"))
                                                .build()
                                )
                                .build()
                )
                .withRequiredDocument(
                        RequiredIdDocument.builder()
                                .withFilter(
                                        OrthogonalRestrictionsFilter.builder()
                                                .withWhitelistedDocumentTypes(Arrays.asList("DRIVING_LICENCE"))
                                                .build()
                                )
                                .build()
                )
                .withRequiredDocument(
                        RequiredSupplementaryDocument.builder()
                                .withObjective(
                                        ProofOfAddressObjective.builder()
                                                .build()
                                )
                                .build()
                )
                .build();

        return docScanClient.createSession(sessionSpec);
    }

    public GetSessionResult getSession(String sessionId) throws DocScanException {
        return docScanClient.getSession(sessionId);
    }

    public String getIframeUrl(CreateSessionResult createSessionResult) {
        String apiUrl = System.getProperty(YotiConstants.PROPERTY_YOTI_DOCS_URL, YotiConstants.DEFAULT_YOTI_DOCS_URL);
        return String.format(IFRAME_URL_FORMAT, apiUrl, createSessionResult.getSessionId(), createSessionResult.getClientSessionToken());
    }

    public Media getMedia(String sessionId, String mediaId) throws DocScanException {
        return docScanClient.getMediaContent(sessionId, mediaId);
    }
}
