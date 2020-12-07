package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface GetSessionResult {

    long getClientSessionTokenTtl();

    String getSessionId();

    String getUserTrackingId();

    String getState();

    String getClientSessionToken();

    String getBiometricConsentTimestamp();

    List<? extends CheckResponse> getChecks();

    ResourceContainer getResources();

    List<AuthenticityCheckResponse> getAuthenticityChecks();

    List<FaceMatchCheckResponse> getFaceMatchChecks();

    /**
     * @deprecated Please use {@code getIdDocumentTextDataChecks()} instead.
     */
    @Deprecated
    List<TextDataCheckResponse> getTextDataChecks();

    List<TextDataCheckResponse> getIdDocumentTextDataChecks();

    List<SupplementaryDocumentTextDataCheckResponse> getSupplementaryDocumentTextDataChecks();

    List<LivenessCheckResponse> getLivenessChecks();

    List<IdDocumentComparisonCheckResponse> getIdDocumentComparisonChecks();

    List<ThirdPartyIdentityCheckResponse> getThirdPartyIdentityChecks();

}
