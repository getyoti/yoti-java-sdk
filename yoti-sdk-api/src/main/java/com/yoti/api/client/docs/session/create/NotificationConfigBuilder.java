package com.yoti.api.client.docs.session.create;

/**
 * Builder to assist in the creation of {@link NotificationConfig}.
 */
public interface NotificationConfigBuilder {

    /**
     * Sets the authorization type to BASIC.  The authorization token
     * will be Base64 encoded by the backend when sent with notifications.
     *
     * @return the builder
     */
    NotificationConfigBuilder withAuthTypeBasic();

    /**
     * Sets the authorization type to BEARER.  The authorization token
     * will be unchanged by the backend when sent with notifications.
     *
     * @return the builder
     */
    NotificationConfigBuilder withAuthTypeBearer();

    /**
     * Sets the authorization token to be included in call-back messages
     *
     * @param authToken the authorization token
     * @return the builder
     */
    NotificationConfigBuilder withAuthToken(String authToken);

    /**
     * Sets the endpoint that notifications should be sent to
     *
     * @param endpoint the endpoint
     * @return the builder
     */
    NotificationConfigBuilder withEndpoint(String endpoint);

    /**
     * Adds RESOURCE_UPDATE to the list of topics that trigger notification messages
     *
     * @return the builder
     */
    NotificationConfigBuilder forResourceUpdate();

    /**
     * Adds TASK_COMPLETION to the list of topics that trigger notification messages
     *
     * @return the builder
     */
    NotificationConfigBuilder forTaskCompletion();

    /**
     * Adds CHECK_COMPLETION to the list of topics that trigger notification messages
     *
     * @return the builder
     */
    NotificationConfigBuilder forCheckCompletion();

    /**
     * Adds SESSION_COMPLETION to the list of topics that trigger notification messages
     *
     * @return the builder
     */
    NotificationConfigBuilder forSessionCompletion();

    /**
     * Adds a topic to the list of topics that trigger notification messages
     *
     * @param topicName the topic name
     * @return the builder
     */
    NotificationConfigBuilder withTopic(String topicName);

    /**
     * Builds the {@link NotificationConfig} using the supplied values
     *
     * @return the built {@link NotificationConfig}
     */
    NotificationConfig build();

}
