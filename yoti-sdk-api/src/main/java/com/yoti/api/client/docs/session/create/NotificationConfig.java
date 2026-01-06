package com.yoti.api.client.docs.session.create;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configures call-back Notifications to some backend endpoint provided by the Relying Business.
 * <p>
 * Notifications can be configured to notify a clients backend of certain events, avoiding the need to poll for the state of the Session.
 */
public class NotificationConfig {

    @JsonProperty("auth_token")
    private final String authToken;

    @JsonProperty("auth_type")
    private final String authType;

    @JsonProperty("endpoint")
    private final String endpoint;

    @JsonProperty("topics")
    private final List<String> topics;

    private NotificationConfig(String authToken, String authType, String endpoint, List<String> topics) {
        this.authToken = authToken;
        this.authType = authType;
        this.endpoint = endpoint;
        this.topics = topics;
    }

    public static NotificationConfig.Builder builder() {
        return new NotificationConfig.Builder();
    }

    /**
     * The authorization token to be included in call-back messages
     *
     * @return the authorization token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * The authorization type to use for the token
     *
     * @return the authorization type
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * The endpoint that notifications should be sent to
     *
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * The list of topics that should trigger notifications
     *
     * @return the list of topics
     */
    public List<String> getTopics() {
        return topics;
    }

    /**
     * Builder to assist in the creation of {@link NotificationConfig}.
     */
    public static class Builder {

        private final List<String> topics = new ArrayList<>();
        private String authToken;
        private String authType;
        private String endpoint;

        private Builder() {
        }

        /**
         * Sets the authorization type to BASIC.  The authorization token
         * will be Base64 encoded by the backend when sent with notifications.
         *
         * @return the builder
         */
        public Builder withAuthTypeBasic() {
            authType = DocScanConstants.BASIC;
            return this;
        }

        /**
         * Sets the authorization type to BEARER.  The authorization token
         * will be unchanged by the backend when sent with notifications.
         *
         * @return the builder
         */
        public Builder withAuthTypeBearer() {
            authType = DocScanConstants.BEARER;
            return this;
        }

        /**
         * Sets the authorization token to be included in call-back messages
         *
         * @param authToken the authorization token
         * @return the builder
         */
        public Builder withAuthToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        /**
         * Sets the endpoint that notifications should be sent to
         *
         * @param endpoint the endpoint
         * @return the builder
         */
        public Builder withEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        /**
         * Adds RESOURCE_UPDATE to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forResourceUpdate() {
            return withTopic(DocScanConstants.RESOURCE_UPDATE);
        }

        /**
         * Adds TASK_COMPLETION to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forTaskCompletion() {
            return withTopic(DocScanConstants.TASK_COMPLETION);
        }

        /**
         * Adds CHECK_COMPLETION to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forCheckCompletion() {
            return withTopic(DocScanConstants.CHECK_COMPLETION);
        }

        /**
         * Adds SESSION_COMPLETION to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forSessionCompletion() {
            return withTopic(DocScanConstants.SESSION_COMPLETION);
        }

        /**
         * Adds CLIENT_SESSION_TOKEN_DELETED to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forClientSessionCompletion() {
            return withTopic(DocScanConstants.CLIENT_SESSION_TOKEN_DELETED);
        }

        /**
         * Adds NEW_PDF_SUPPLIED to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forNewPdfSupplied() {
            return withTopic(DocScanConstants.NEW_PDF_SUPPLIED);
        }

        /**
         * Adds INSTRUCTIONS_EMAIL_REQUESTED to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forInstructionsEmailRequested() {
            return withTopic(DocScanConstants.INSTRUCTIONS_EMAIL_REQUESTED);
        }

        /**
         * Adds THANK_YOU_EMAIL_REQUESTED to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forThankYouEmailRequested() {
            return withTopic(DocScanConstants.THANK_YOU_EMAIL_REQUESTED);
        }

        /**
         * Adds FIRST_BRANCH_VISIT to the list of topics that trigger notification messages
         *
         * @return the builder
         */
        public Builder forFirstBranchVisit() {
            return withTopic(DocScanConstants.FIRST_BRANCH_VISIT);
        }

        /**
         * Adds a topic to the list of topics that trigger notification messages
         *
         * @param topicName the topic name
         * @return the builder
         */
        public Builder withTopic(String topicName) {
            this.topics.add(topicName);
            return this;
        }

        public NotificationConfig build() {
            return new NotificationConfig(authToken, authType, endpoint, topics);
        }

    }

}
