package com.yoti.api.client.docs.session.create;

import com.yoti.api.client.docs.session.create.check.RequestedCheck;
import com.yoti.api.client.docs.session.create.filters.RequiredDocument;
import com.yoti.api.client.docs.session.create.task.RequestedTask;

import java.util.List;

/**
 * Definition for the Doc Scan Session to be created
 */
public interface SessionSpec {

    /**
     * client-session-token time-to-live to apply to the created session
     *
     * @return the client session token TTL
     */
    Integer getClientSessionTokenTtl();

    /**
     * time-to-live used for all Resources created in the course of the session
     *
     * @return the resources TTL
     */
    Integer getResourcesTtl();

    /**
     * user tracking id, for the Relying Business to track returning users
     *
     * @return the user tracking ID
     */
    String getUserTrackingId();

    /**
     * {@link NotificationConfig} for configuring call-back messages
     *
     * @return the notification configuration
     */
    NotificationConfig getNotifications();

    /**
     * List of {@link RequestedCheck} objects defining the Checks to be performed on each Document
     *
     * @return the requested checks
     */
    List<RequestedCheck> getRequestedChecks();

    /**
     * List of {@link RequestedTask} objects defining the Tasks to be performed on each Document
     *
     * @return the requested tasks
     */
    List<RequestedTask> getRequestedTasks();

    /**
     * Retrieves the SDK configuration set on the session specification
     *
     * @return the {@link SdkConfig}
     */
    SdkConfig getSdkConfig();

    /**
     * List of {@link RequiredDocument} defining the documents required from the client
     *
     * @return the required documents
     */
    List<RequiredDocument> getRequiredDocuments();

    /**
     * Whether or not to block the collection of biometric consent
     *
     * @return should block biometric consent
     */
    Boolean getBlockBiometricConsent();

}
