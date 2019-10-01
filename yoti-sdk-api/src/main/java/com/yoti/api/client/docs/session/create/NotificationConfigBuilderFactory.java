package com.yoti.api.client.docs.session.create;

import java.util.ServiceLoader;

/**
 * Builder to assist in the creation of {@link NotificationConfig}.
 */
public abstract class NotificationConfigBuilderFactory {

    public static NotificationConfigBuilderFactory newInstance() {
        ServiceLoader<NotificationConfigBuilderFactory> notificationConfigBuilderServiceLoader = ServiceLoader.load(NotificationConfigBuilderFactory.class);
        if (!notificationConfigBuilderServiceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + NotificationConfigBuilderFactory.class.getSimpleName());
        }
        return notificationConfigBuilderServiceLoader.iterator().next();
    }

    public abstract NotificationConfigBuilder create();

}
