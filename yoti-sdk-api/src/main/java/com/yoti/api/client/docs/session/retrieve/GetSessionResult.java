package com.yoti.api.client.docs.session.retrieve;

import java.util.ArrayList;
import java.util.List;

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

    public String getSessionId() {
        return sessionId;
    }

    public long getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
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

    public String getUserTrackingId() {
        return userTrackingId;
    }

    public List<AuthenticityCheckResponse> getAuthenticityChecks() {
        return filterChecksByType(AuthenticityCheckResponse.class);
    }

    public List<FaceMatchCheckResponse> getFaceMatchChecks() {
        return filterChecksByType(FaceMatchCheckResponse.class);
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

    private <T extends CheckResponse> List<T> filterChecksByType(Class<T> clazz) {
        List<T> filteredList = new ArrayList<>();
        for (CheckResponse checkResponse : checks) {
            if (clazz.isInstance(checkResponse)) {
                filteredList.add(clazz.cast(checkResponse));
            }
        }
        return filteredList;
    }

}
