package com.yoti.api.client.docs.session.retrieve;

import java.util.List;
import java.util.stream.Collectors;

import com.yoti.api.client.docs.session.retrieve.identityprofile.advanced.AdvancedIdentityProfileResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSessionResult {

    @JsonProperty("client_session_token_ttl")
    private long clientSessionTokenTtl;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("user_tracking_id")
    private String userTrackingId;

    @JsonProperty("state")
    private String state;

    @JsonProperty("client_session_token")
    private String clientSessionToken;

    @JsonProperty("biometric_consent")
    private String biometricConsent;

    @JsonProperty("checks")
    private List<? extends CheckResponse> checks;

    @JsonProperty("resources")
    private ResourceContainer resources;

    @JsonProperty("identity_profile")
    private IdentityProfileResponse identityProfile;

    @JsonProperty("advanced_identity_profile")
    private AdvancedIdentityProfileResponse advancedIdentityProfile;

    @JsonProperty("identity_profile_preview")
    private IdentityProfilePreviewResponse identityProfilePreview;

    @JsonProperty("advanced_identity_profile_preview")
    private IdentityProfilePreviewResponse advancedIdentityProfilePreview;

    @JsonProperty("import_token")
    private ImportTokenResponse importToken;

    public long getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserTrackingId() {
        return userTrackingId;
    }

    public String getState() {
        return state;
    }

    public String getClientSessionToken() {
        return clientSessionToken;
    }

    public String getBiometricConsentTimestamp() {
        return biometricConsent;
    }

    public List<? extends CheckResponse> getChecks() {
        return checks;
    }

    public ResourceContainer getResources() {
        return resources;
    }

    public IdentityProfileResponse getIdentityProfile() {
        return identityProfile;
    }

    public AdvancedIdentityProfileResponse getAdvancedIdentityProfile() {
        return advancedIdentityProfile;
    }

    public IdentityProfilePreviewResponse getIdentityProfilePreview() {
        return identityProfilePreview;
    }

    public IdentityProfilePreviewResponse getAdvancedIdentityProfilePreview() {
        return advancedIdentityProfilePreview;
    }

    public ImportTokenResponse getImportToken() {
        return importToken;
    }

    public ResourceContainer getResourcesForCheck(String checkId) {
        CheckResponse checkResponse = this.checks.stream()
                .filter(check -> check.getId().equals(checkId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Check not found"));
        return resources.filterForCheck(checkResponse);
    }

    public List<AuthenticityCheckResponse> getAuthenticityChecks() {
        return filterChecksByType(AuthenticityCheckResponse.class);
    }

    public List<FaceMatchCheckResponse> getFaceMatchChecks() {
        return filterChecksByType(FaceMatchCheckResponse.class);
    }

    public List<FaceComparisonCheckResponse> getFaceComparisonChecks() {
        return filterChecksByType(FaceComparisonCheckResponse.class);
    }

    public List<TextDataCheckResponse> getIdDocumentTextDataChecks() {
        return filterChecksByType(TextDataCheckResponse.class);
    }

    public List<SupplementaryDocumentTextDataCheckResponse> getSupplementaryDocumentTextDataChecks() {
        return filterChecksByType(SupplementaryDocumentTextDataCheckResponse.class);
    }

    public List<LivenessCheckResponse> getLivenessChecks() {
        return filterChecksByType(LivenessCheckResponse.class);
    }

    public List<IdDocumentComparisonCheckResponse> getIdDocumentComparisonChecks() {
        return filterChecksByType(IdDocumentComparisonCheckResponse.class);
    }

    public List<ThirdPartyIdentityCheckResponse> getThirdPartyIdentityChecks() {
        return filterChecksByType(ThirdPartyIdentityCheckResponse.class);
    }

    public List<SynecticsIdentityFraudCheckResponse> getSynecticsIdentityFraudChecks() {
        return filterChecksByType(SynecticsIdentityFraudCheckResponse.class);
    }

    public List<WatchlistScreeningCheckResponse> getWatchlistScreeningChecks() {
        return filterChecksByType(WatchlistScreeningCheckResponse.class);
    }

    public List<WatchlistAdvancedCaCheckResponse> getWatchlistAdvancedCaChecks() {
        return filterChecksByType(WatchlistAdvancedCaCheckResponse.class);
    }

    public List<IbvVisualReviewCheckResponse> getIbvVisualReviewChecks() {
        return filterChecksByType(IbvVisualReviewCheckResponse.class);
    }

    public List<DocumentSchemeValidityCheckResponse> getDocumentSchemeValidityChecks() {
        return filterChecksByType(DocumentSchemeValidityCheckResponse.class);
    }

    public List<ProfileDocumentMatchCheckResponse> getProfileDocumentMatchChecks() {
        return filterChecksByType(ProfileDocumentMatchCheckResponse.class);
    }

    public List<ThirdPartyIdentityFraudOneCheckResponse> getThirdPartyIdentityFraudOneChecks() {
        return filterChecksByType(ThirdPartyIdentityFraudOneCheckResponse.class);
    }

    private <T extends CheckResponse> List<T> filterChecksByType(Class<T> clazz) {
        return checks.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

}
