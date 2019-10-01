package com.yoti.api.client.docs.session.create.check;

/**
 * {@link RequestedCheck} requests creation of a Check to be performed on a document
 *
 * @param <T> class that implements {@link RequestedCheckConfig}
 */
public interface RequestedCheck<T extends RequestedCheckConfig> {

    /**
     * Return the type of the Check to create
     *
     * @return the check type
     */
    String getType();

    /**
     * Return configuration to apply to the Check
     *
     * @return the configuration
     */
    T getConfig();

}
