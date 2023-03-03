package com.yoti.api.client.docs.session.retrieve;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSessionResult {

    @JsonProperty(Property.CLIENT_SESSION_TOKEN_TTL)
    private long clientSessionTokenTtl;

    @JsonProperty(Property.SESSION_ID)
    private String sessionId;

    @JsonProperty(Property.USER_TRACKING_ID)
    private String userTrackingId;

    @JsonProperty(Property.STATE)
    private String state;

    @JsonProperty(Property.CLIENT_SESSION_TOKEN)
    private String clientSessionToken;

    @JsonProperty(Property.BIOMETRIC_CONSENT)
    private String biometricConsent;

    @JsonProperty(Property.CHECKS)
    private List<? extends CheckResponse> checks;

    @JsonProperty(Property.RESOURCES)
    private ResourceContainer resources;

    @JsonProperty(Property.IDENTITY_PROFILE)
    private IdentityProfileResponse identityProfile;

    @JsonProperty(Property.IDENTITY_PROFILE_PREVIEW)
    private IdentityProfilePreviewResponse identityProfilePreview;

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

    public IdentityProfilePreviewResponse getIdentityProfilePreview() {
        return identityProfilePreview;
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

    private static final class Property {

        private static final String CLIENT_SESSION_TOKEN_TTL = "client_session_token_ttl";
        private static final String SESSION_ID = "session_id";
        private static final String USER_TRACKING_ID = "user_tracking_id";
        private static final String STATE = "state";
        private static final String CLIENT_SESSION_TOKEN = "client_session_token";
        private static final String BIOMETRIC_CONSENT = "biometric_consent";
        private static final String CHECKS = "checks";
        private static final String RESOURCES = "resources";
        private static final String IDENTITY_PROFILE = "identity_profile";
        private static final String IDENTITY_PROFILE_PREVIEW = "identity_profile_preview";

        private Property() { }

    }

}
