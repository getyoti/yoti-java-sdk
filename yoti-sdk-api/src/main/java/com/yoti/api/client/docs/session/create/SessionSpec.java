package com.yoti.api.client.docs.session.create;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.session.create.check.RequestedCheck;
import com.yoti.api.client.docs.session.create.filters.RequiredDocument;
import com.yoti.api.client.docs.session.create.task.RequestedTask;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Definition for the Doc Scan Session to be created
 */
public class SessionSpec {

    @JsonProperty("client_session_token_ttl")
    private final Integer clientSessionTokenTtl;

    @JsonProperty("resources_ttl")
    private final Integer resourcesTtl;

    @JsonProperty("user_tracking_id")
    private final String userTrackingId;

    @JsonProperty("notifications")
    private final NotificationConfig notifications;

    @JsonProperty("requested_checks")
    private final List<RequestedCheck<?>> requestedChecks;

    @JsonProperty("requested_tasks")
    private final List<RequestedTask<?>> requestedTasks;

    @JsonProperty("sdk_config")
    private final SdkConfig sdkConfig;

    @JsonProperty("required_documents")
    private final List<RequiredDocument> requiredDocuments;

    @JsonProperty("block_biometric_consent")
    private final Boolean blockBiometricConsent;

    SessionSpec(Integer clientSessionTokenTtl,
            Integer resourcesTtl,
            String userTrackingId,
            NotificationConfig notifications,
            List<RequestedCheck<?>> requestedChecks,
            List<RequestedTask<?>> requestedTasks,
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

    public static SessionSpec.Builder builder() {
        return new SessionSpec.Builder();
    }

    /**
     * client-session-token time-to-live to apply to the created session
     *
     * @return the client session token TTL
     */
    public Integer getClientSessionTokenTtl() {
        return clientSessionTokenTtl;
    }

    /**
     * time-to-live used for all Resources created in the course of the session
     *
     * @return the resources TTL
     */
    public Integer getResourcesTtl() {
        return resourcesTtl;
    }

    /**
     * user tracking id, for the Relying Business to track returning users
     *
     * @return the user tracking ID
     */
    public String getUserTrackingId() {
        return userTrackingId;
    }

    /**
     * {@link NotificationConfig} for configuring call-back messages
     *
     * @return the notification configuration
     */
    public NotificationConfig getNotifications() {
        return notifications;
    }

    /**
     * List of {@link RequestedCheck} objects defining the Checks to be performed on each Document
     *
     * @return the requested checks
     */
    public List<RequestedCheck<?>> getRequestedChecks() {
        return requestedChecks;
    }

    /**
     * List of {@link RequestedTask} objects defining the Tasks to be performed on each Document
     *
     * @return the requested tasks
     */
    public List<RequestedTask<?>> getRequestedTasks() {
        return requestedTasks;
    }

    /**
     * Retrieves the SDK configuration set on the session specification
     *
     * @return the {@link SdkConfig}
     */
    public SdkConfig getSdkConfig() {
        return sdkConfig;
    }

    /**
     * List of {@link RequiredDocument} defining the documents required from the client
     *
     * @return the required documents
     */
    public List<RequiredDocument> getRequiredDocuments() {
        return requiredDocuments;
    }

    /**
     * Whether or not to block the collection of biometric consent
     *
     * @return should block biometric consent
     */
    public Boolean getBlockBiometricConsent() {
        return blockBiometricConsent;
    }

    public static class Builder {

        private final List<RequestedCheck<?>> requestedChecks = new ArrayList<>();
        private final List<RequestedTask<?>> requestedTasks = new ArrayList<>();
        private final List<RequiredDocument> requiredDocuments = new ArrayList<>();
        private Integer clientSessionTokenTtl;
        private Integer resourcesTtl;
        private String userTrackingId;
        private NotificationConfig notifications;
        private SdkConfig sdkConfig;
        private Boolean blockBiometricConsent;

        private Builder() {
        }

        /**
         * Sets the client session token TTL (time-to-live)
         *
         * @param clientSessionTokenTtl the client session token TTL
         * @return the builder
         */
        public Builder withClientSessionTokenTtl(Integer clientSessionTokenTtl) {
            this.clientSessionTokenTtl = clientSessionTokenTtl;
            return this;
        }

        /**
         * Sets the resources TTL (time-to-live)
         *
         * @param resourcesTtl the resources TTL
         * @return the builder
         */
        public Builder withResourcesTtl(Integer resourcesTtl) {
            this.resourcesTtl = resourcesTtl;
            return this;
        }

        /**
         * Sets the user tracking ID
         *
         * @param userTrackingId the user tracking ID
         * @return the builder
         */
        public Builder withUserTrackingId(String userTrackingId) {
            this.userTrackingId = userTrackingId;
            return this;
        }

        /**
         * Sets the {@link NotificationConfig}
         *
         * @param notifications the {@link NotificationConfig}
         * @return the builder
         */
        public Builder withNotifications(NotificationConfig notifications) {
            this.notifications = notifications;
            return this;
        }

        /**
         * Adds a {@link RequestedCheck} to the list
         *
         * @param requestedCheck the {@link RequestedCheck}
         * @return the builder
         */
        public Builder withRequestedCheck(RequestedCheck<?> requestedCheck) {
            this.requestedChecks.add(requestedCheck);
            return this;
        }

        /**
         * Adds a {@link RequestedTask} to the list
         *
         * @param requestedTask the {@link RequestedTask}
         * @return
         */
        public Builder withRequestedTask(RequestedTask<?> requestedTask) {
            this.requestedTasks.add(requestedTask);
            return this;
        }

        /**
         * Sets the {@link SdkConfig}
         *
         * @param sdkConfig the {@link SdkConfig}
         * @return the builder
         */
        public Builder withSdkConfig(SdkConfig sdkConfig) {
            this.sdkConfig = sdkConfig;
            return this;
        }

        /**
         * Adds a {@link RequiredDocument} to the list
         *
         * @param requiredDocument the {@code RequiredDocument}
         * @return the builder
         */
        public Builder withRequiredDocument(RequiredDocument requiredDocument) {
            this.requiredDocuments.add(requiredDocument);
            return this;
        }

        /**
         * Sets whether or not to block the collection of biometric consent
         *
         * @param blockBiometricConsent block collection of biometric consent
         * @return the builder
         */
        public Builder withBlockBiometricConsent(boolean blockBiometricConsent) {
            this.blockBiometricConsent = blockBiometricConsent;
            return this;
        }

        /**
         * Builds the {@link SessionSpec} based on the values supplied to the builder
         *
         * @return the built {@link SessionSpec}
         */
        public SessionSpec build() {
            return new SessionSpec(
                    clientSessionTokenTtl,
                    resourcesTtl,
                    userTrackingId,
                    notifications,
                    requestedChecks,
                    requestedTasks,
                    sdkConfig,
                    requiredDocuments,
                    blockBiometricConsent
            );
        }
    }

}
