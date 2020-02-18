package com.yoti.api.client.docs.session.create;

import java.util.List;

/**
 * Configures call-back Notifications to some backend endpoint provided by the Relying Business.
 * <p>
 * Notifications can be configured to notify a clients backend of certain events, avoiding the need to poll for the state of the Session.
 */
public interface NotificationConfig {

    /**
     * The authorization token to be included in call-back messages
     *
     * @return the authorization token
     */
    String getAuthToken();

    /**
     * The endpoint that notifications should be sent to
     *
     * @return the endpoint
     */
    String getEndpoint();


    /**
     * The list of topics that should trigger notifications
     *
     * @return the list of topics
     */
    List<String> getTopics();

}
