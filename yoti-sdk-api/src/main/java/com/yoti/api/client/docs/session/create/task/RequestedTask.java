package com.yoti.api.client.docs.session.create.task;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link RequestedTask} requests creation of a Task to be performed on each document
 *
 * @param <T> class that extends {@link RequestedTaskConfig}
 */
public abstract class RequestedTask<T extends RequestedTaskConfig> {

    /**
     * Returns the type of the Task to create
     *
     * @return the type
     */
    @JsonProperty("type")
    public abstract String getType();

    /**
     * Configuration to apply to the Task
     *
     * @return the task configuration
     */
    @JsonProperty("config")
    public abstract T getConfig();

    public static RequestedIdDocTextExtractionTask.Builder forIdDocTextExtractionTask() {
        return RequestedIdDocTextExtractionTask.builder();
    }

    public static RequestedSupplementaryDocTextExtractionTask.Builder forSupplementaryDocTextExtractionTask() {
        return RequestedSupplementaryDocTextExtractionTask.builder();
    }

}
