package com.yoti.api.client.docs.session.create;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;

public class SimpleNotificationConfigBuilder implements NotificationConfigBuilder {

    private final List<String> topics = new ArrayList<>();
    private String authToken;
    private String authType;
    private String endpoint;

    @Override
    public NotificationConfigBuilder withAuthTypeBasic() {
        authType = DocScanConstants.BASIC;
        return this;
    }

    @Override
    public NotificationConfigBuilder withAuthTypeBearer() {
        authType = DocScanConstants.BEARER;
        return this;
    }

    @Override
    public NotificationConfigBuilder withAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    @Override
    public NotificationConfigBuilder withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Override
    public NotificationConfigBuilder forResourceUpdate() {
        return withTopic(DocScanConstants.RESOURCE_UPDATE);
    }

    @Override
    public NotificationConfigBuilder forTaskCompletion() {
        return withTopic(DocScanConstants.TASK_COMPLETION);
    }

    @Override
    public NotificationConfigBuilder forCheckCompletion() {
        return withTopic(DocScanConstants.CHECK_COMPLETION);
    }

    @Override
    public NotificationConfigBuilder forSessionCompletion() {
        return withTopic(DocScanConstants.SESSION_COMPLETION);
    }

    @Override
    public NotificationConfigBuilder withTopic(String topicName) {
        this.topics.add(topicName);
        return this;
    }

    @Override
    public NotificationConfig build() {
        return new SimpleNotificationConfig(authToken, authType, endpoint, topics);
    }

}
