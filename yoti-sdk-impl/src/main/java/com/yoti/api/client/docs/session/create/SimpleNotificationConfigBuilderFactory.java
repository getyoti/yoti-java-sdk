package com.yoti.api.client.docs.session.create;

public class SimpleNotificationConfigBuilderFactory extends NotificationConfigBuilderFactory {

    @Override
    public NotificationConfigBuilder create() {
        return new SimpleNotificationConfigBuilder();
    }

}
