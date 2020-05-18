package com.yoti.api.client.docs.session.create;

import com.yoti.api.client.docs.session.create.check.RequestedCheck;
import com.yoti.api.client.docs.session.create.filters.RequiredDocument;
import com.yoti.api.client.docs.session.create.task.RequestedTask;

/**
 * Builder to assist the creation of {@link SessionSpec}.
 */
public interface SessionSpecBuilder {

    /**
     * Sets the client session token TTL (time-to-live)
     *
     * @param clientSessionTokenTtl the client session token TTL
     * @return the builder
     */
    SessionSpecBuilder withClientSessionTokenTtl(Integer clientSessionTokenTtl);

    /**
     * Sets the resources TTL (time-to-live)
     *
     * @param resourcesTtl the resources TTL
     * @return the builder
     */
    SessionSpecBuilder withResourcesTtl(Integer resourcesTtl);

    /**
     * Sets the user tracking ID
     *
     * @param userTrackingId the user tracking ID
     * @return the builder
     */
    SessionSpecBuilder withUserTrackingId(String userTrackingId);

    /**
     * Sets the {@link NotificationConfig}
     *
     * @param notifications the {@link NotificationConfig}
     * @return the builder
     */
    SessionSpecBuilder withNotifications(NotificationConfig notifications);

    /**
     * Adds a {@link RequestedCheck} to the list
     *
     * @param requestedCheck the {@link RequestedCheck}
     * @return the builder
     */
    SessionSpecBuilder withRequestedCheck(RequestedCheck requestedCheck);

    /**
     * Adds a {@link RequestedTask} to the list
     *
     * @param requestedTasks the {@link RequestedTask}
     * @return
     */
    SessionSpecBuilder withRequestedTask(RequestedTask requestedTasks);

    /**
     * Sets the {@link SdkConfig}
     *
     * @param sdkConfig the {@link SdkConfig}
     * @return the builder
     */
    SessionSpecBuilder withSdkConfig(SdkConfig sdkConfig);

    /**
     * Adds a {@link RequiredDocument} to the list
     *
     * @param requiredDocument the {@code RequiredDocument}
     * @return the builder
     */
    SessionSpecBuilder withRequiredDocument(RequiredDocument requiredDocument);

    /**
     * Builds the {@link SessionSpec} based on the values supplied to the builder
     *
     * @return the built {@link SessionSpec}
     */
    SessionSpec build();

}
