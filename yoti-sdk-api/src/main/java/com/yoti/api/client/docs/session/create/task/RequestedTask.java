package com.yoti.api.client.docs.session.create.task;

/**
 * {@link RequestedTask} requests creation of a Task to be performed on each document
 *
 * @param <T> class that extends {@link RequestedTaskConfig}
 */
public interface RequestedTask<T extends RequestedTaskConfig> {

    /**
     * Returns the type of the Task to create
     *
     * @return the type
     */
    String getType();

    /**
     * Configuration to apply to the Task
     *
     * @return the task configuration
     */
    T getConfig();

}
