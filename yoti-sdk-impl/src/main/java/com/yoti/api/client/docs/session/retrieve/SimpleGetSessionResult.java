package com.yoti.api.client.docs.session.retrieve;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleGetSessionResult implements GetSessionResult {

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
    private List<? extends SimpleCheckResponse> checks;

    @JsonProperty("resources")
    private SimpleResourceContainer resources;

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public long getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getClientSessionToken() {
        return clientSessionToken;
    }

    @Override
    public String getBiometricConsentTimestamp() {
        return biometricConsent;
    }

    @Override
    public List<? extends CheckResponse> getChecks() {
        return checks;
    }

    @Override
    public ResourceContainer getResources() {
        return resources;
    }

    @Override
    public String getUserTrackingId() {
        return userTrackingId;
    }

    @Override
    public List<AuthenticityCheckResponse> getAuthenticityChecks() {
        return filterChecksByType(AuthenticityCheckResponse.class);
    }

    @Override
    public List<FaceMatchCheckResponse> getFaceMatchChecks() {
        return filterChecksByType(FaceMatchCheckResponse.class);
    }

    @Override
    public List<TextDataCheckResponse> getTextDataChecks() {
        return getIdDocumentTextDataChecks();
    }

    @Override
    public List<TextDataCheckResponse> getIdDocumentTextDataChecks() {
        return filterChecksByType(TextDataCheckResponse.class);
    }

    @Override
    public List<SupplementaryDocumentTextDataCheckResponse> getSupplementaryDocumentTextDataChecks() {
        return filterChecksByType(SupplementaryDocumentTextDataCheckResponse.class);
    }

    @Override
    public List<LivenessCheckResponse> getLivenessChecks() {
        return filterChecksByType(LivenessCheckResponse.class);
    }

    @Override
    public List<IdDocumentComparisonCheckResponse> getIdDocumentComparisonChecks() {
        return filterChecksByType(IdDocumentComparisonCheckResponse.class);
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
