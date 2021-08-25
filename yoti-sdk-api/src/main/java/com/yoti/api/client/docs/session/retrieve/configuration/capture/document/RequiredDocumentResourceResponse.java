package com.yoti.api.client.docs.session.retrieve.configuration.capture.document;

import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.RequiredResourceResponse;
import com.yoti.api.client.docs.session.retrieve.configuration.capture.task.RequestedTaskResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequiredIdDocumentResourceResponse.class, name = DocScanConstants.ID_DOCUMENT),
        @JsonSubTypes.Type(value = RequiredSupplementaryDocumentResource.class, name = DocScanConstants.SUPPLEMENTARY_DOCUMENT)
})
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
