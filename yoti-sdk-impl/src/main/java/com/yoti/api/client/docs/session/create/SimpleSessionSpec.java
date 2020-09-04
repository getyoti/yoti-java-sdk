package com.yoti.api.client.docs.session.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.yoti.api.client.docs.session.create.check.RequestedCheck;
import com.yoti.api.client.docs.session.create.filters.RequiredDocument;
import com.yoti.api.client.docs.session.create.task.RequestedTask;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleSessionSpec implements SessionSpec {

    @JsonProperty("client_session_token_ttl")
    private final Integer clientSessionTokenTtl;

    @JsonProperty("resources_ttl")
    private final Integer resourcesTtl;

    @JsonProperty("user_tracking_id")
    private final String userTrackingId;

    @JsonProperty("notifications")
    private final NotificationConfig notifications;

    @JsonProperty("requested_checks")
    private final List<RequestedCheck> requestedChecks;

    @JsonProperty("requested_tasks")
    private final List<RequestedTask> requestedTasks;

    @JsonProperty("sdk_config")
    private final SdkConfig sdkConfig;

    @JsonProperty("required_documents")
    private final List<RequiredDocument> requiredDocuments;

    @JsonProperty("block_biometric_consent")
    private final Boolean blockBiometricConsent;

    public SimpleSessionSpec(Integer clientSessionTokenTtl,
            Integer resourcesTtl,
            String userTrackingId,
            NotificationConfig notifications,
            List<RequestedCheck> requestedChecks,
            List<RequestedTask> requestedTasks,
            SdkConfig sdkConfig,
            List<RequiredDocument> requiredDocuments,
            Boolean blockBiometricConsent) {
        this.clientSessionTokenTtl = clientSessionTokenTtl;
        this.resourcesTtl = resourcesTtl;
        this.userTrackingId = userTrackingId;
        this.notifications = notifications;
        this.requestedChecks = requestedChecks;
        this.requestedTasks = requestedTasks;
        this.sdkConfig = sdkConfig;
        this.requiredDocuments = requiredDocuments;
        this.blockBiometricConsent = blockBiometricConsent;
    }

    @Override
    public Integer getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
    }

    @Override
    public Integer getResourcesTtl() {
        return resourcesTtl;
    }

    @Override
    public String getUserTrackingId() {
        return userTrackingId;
    }

    @Override
    public NotificationConfig getNotifications() {
        return notifications;
    }

    @Override
    public List<RequestedCheck> getRequestedChecks() {
        return requestedChecks;
    }

    @Override
    public List<RequestedTask> getRequestedTasks() {
        return requestedTasks;
    }

    @Override
    public SdkConfig getSdkConfig() {
        return sdkConfig;
    }

    @Override
    public List<RequiredDocument> getRequiredDocuments() {
        return requiredDocuments;
    }

    @Override
    public Boolean getBlockBiometricConsent() {
        return blockBiometricConsent;
    }

}
