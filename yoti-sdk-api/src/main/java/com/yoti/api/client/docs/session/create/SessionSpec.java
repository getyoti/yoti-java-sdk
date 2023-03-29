package com.yoti.api.client.docs.session.create;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yoti.api.client.docs.session.create.check.RequestedCheck;
import com.yoti.api.client.docs.session.create.filters.RequiredDocument;
import com.yoti.api.client.docs.session.create.task.RequestedTask;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Definition for the Doc Scan Session to be created
 */
public class SessionSpec {

    @JsonProperty(Property.CLIENT_SESSION_TOKEN_TTL)
    private final Integer clientSessionTokenTtl;

    @JsonProperty(Property.SESSION_DEADLINE)
    private final ZonedDateTime sessionDeadline;

    @JsonProperty(Property.RESOURCES_TTL)
    private final Integer resourcesTtl;

    @JsonProperty(Property.USER_TRACKING_ID)
    private final String userTrackingId;

    @JsonProperty(Property.NOTIFICATIONS)
    private final NotificationConfig notifications;

    @JsonProperty(Property.REQUESTED_CHECKS)
    private final List<RequestedCheck<?>> requestedChecks;

    @JsonProperty(Property.REQUESTED_TASKS)
    private final List<RequestedTask<?>> requestedTasks;

    @JsonProperty(Property.SDK_CONFIG)
    private final SdkConfig sdkConfig;

    @JsonProperty(Property.REQUIRED_DOCUMENTS)
    private final List<RequiredDocument> requiredDocuments;

    @JsonProperty(Property.BLOCK_BIOMETRIC_CONSENT)
    private final Boolean blockBiometricConsent;

    @JsonProperty(Property.IBV_OPTIONS)
    private final IbvOptions ibvOptions;

    @JsonProperty(Property.IDENTITY_PROFILE_REQUIREMENTS)
    private final Map<String, Object> identityProfile;

    @JsonProperty(Property.SUBJECT)
    private final Map<String, Object> subject;

    SessionSpec(Integer clientSessionTokenTtl,
            Integer resourcesTtl,
            String userTrackingId,
            NotificationConfig notifications,
            List<RequestedCheck<?>> requestedChecks,
            List<RequestedTask<?>> requestedTasks,
            SdkConfig sdkConfig,
            List<RequiredDocument> requiredDocuments,
            Boolean blockBiometricConsent,
            IbvOptions ibvOptions,
            ZonedDateTime sessionDeadline,
            Map<String, Object> identityProfile,
            Map<String, Object> subject) {
        this.clientSessionTokenTtl = clientSessionTokenTtl;
        this.resourcesTtl = resourcesTtl;
        this.userTrackingId = userTrackingId;
        this.notifications = notifications;
        this.requestedChecks = requestedChecks;
        this.requestedTasks = requestedTasks;
        this.sdkConfig = sdkConfig;
        this.requiredDocuments = requiredDocuments;
        this.blockBiometricConsent = blockBiometricConsent;
        this.ibvOptions = ibvOptions;
        this.sessionDeadline = sessionDeadline;
        this.identityProfile = identityProfile;
        this.subject = subject;
    }

    public static Builder builder() {
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

    /**
     * The options that define if a session will be required to be performed
     * using In-Branch Verification
     *
     * @return the IBV options
     */
    public IbvOptions getIbvOptions() {
        return ibvOptions;
    }

    /**
     * The deadline that the session needs to be completed by.
     *
     * @return the session deadline
     */
    public ZonedDateTime getSessionDeadline() {
        return sessionDeadline;
    }

    /**
     * Defines a required identity profile within the scope of a trust framework and scheme.
     *
     * @return Identity Profile
     */
    public Object getIdentityProfile() {
        return identityProfile;
    }

    /**
     * The subject for which the identity assertion will be performed for the session.
     *
     * @return subject
     */
    public Map<String, Object> getSubject() {
        return subject;
    }

    public static class Builder {

        private final List<RequestedCheck<?>> requestedChecks;
        private final List<RequestedTask<?>> requestedTasks;
        private final List<RequiredDocument> requiredDocuments;
        private Integer clientSessionTokenTtl;
        private Integer resourcesTtl;
        private String userTrackingId;
        private NotificationConfig notifications;
        private SdkConfig sdkConfig;
        private Boolean blockBiometricConsent;
        private IbvOptions ibvOptions;
        private ZonedDateTime sessionDeadline;
        private Map<String, Object> identityProfile;
        private Map<String, Object> subject;

        private Builder() {
            requestedChecks = new ArrayList<>();
            requestedTasks = new ArrayList<>();
            requiredDocuments = new ArrayList<>();
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
         * @return the builder
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
         * Sets whether to block the collection of biometric consent
         *
         * @param blockBiometricConsent block collection of biometric consent
         * @return the builder
         */
        public Builder withBlockBiometricConsent(boolean blockBiometricConsent) {
            this.blockBiometricConsent = blockBiometricConsent;
            return this;
        }

        /**
         * Sets the options that define if a session will be required to be performed
         * using In-Branch Verification
         *
         * @param ibvOptions the IBV options
         * @return the builder
         */
        public Builder withIbvOptions(IbvOptions ibvOptions) {
            this.ibvOptions = ibvOptions;
            return this;
        }

        /**
         * Sets the deadline that the session must be completed by.
         *
         * @param sessionDeadline the session deadline
         * @return the builder
         */
        public Builder withSessionDeadline(ZonedDateTime sessionDeadline) {
            this.sessionDeadline = sessionDeadline;
            return this;
        }

        /**
         * Sets the Identity Profile Requirements for the session
         *
         * @param identityProfile the Identity Profile
         * @return the Builder
         */
        public Builder withIdentityProfile(Map<String, Object> identityProfile) {
            this.identityProfile = identityProfile;
            return this;
        }

        /**
         * Sets the subject for which the identity assertion will be performed for the session.
         *
         * @param subject the subject
         * @return the Builder
         */
        public Builder withSubject(Map<String, Object> subject) {
            this.subject = subject;
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
                    blockBiometricConsent,
                    ibvOptions,
                    sessionDeadline,
                    identityProfile,
                    subject
            );
        }
    }

    private static final class Property {

        private static final String CLIENT_SESSION_TOKEN_TTL = "client_session_token_ttl";
        private static final String SESSION_DEADLINE = "session_deadline";
        private static final String RESOURCES_TTL = "resources_ttl";
        private static final String USER_TRACKING_ID = "user_tracking_id";
        private static final String NOTIFICATIONS = "notifications";
        private static final String REQUESTED_CHECKS = "requested_checks";
        private static final String REQUESTED_TASKS = "requested_tasks";
        private static final String SDK_CONFIG = "sdk_config";
        private static final String REQUIRED_DOCUMENTS = "required_documents";
        private static final String BLOCK_BIOMETRIC_CONSENT = "block_biometric_consent";
        private static final String IBV_OPTIONS = "ibv_options";
        private static final String IDENTITY_PROFILE_REQUIREMENTS = "identity_profile_requirements";
        private static final String SUBJECT = "subject";

        private Property() { }

    }

}
