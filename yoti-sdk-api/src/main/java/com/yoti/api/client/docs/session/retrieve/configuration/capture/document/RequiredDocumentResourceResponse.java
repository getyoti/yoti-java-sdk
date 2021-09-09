package com.yoti.api.client.docs.session.retrieve.configuration.capture.document;

import java.util.List;

import com.yoti.api.client.docs.session.retrieve.configuration.capture.RequiredResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.task.RequestedTaskResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RequiredDocumentResourceResponse extends RequiredResourceResponse {

    @JsonProperty("requested_tasks")
    private List<RequestedTaskResponse> requestedTasks;

    /**
     * Returns any tasks that need to be completed as part of the document
     * requirement.
     *
     * @return the requested tasks
     */
    public List<RequestedTaskResponse> getRequestedTasks() {
        return requestedTasks;
    }

}
