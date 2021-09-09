package com.yoti.api.client.docs.session.retrieve.configuration.capture.task;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, defaultImpl = UnknownRequestedTaskResponse.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestedIdDocTaskResponse.class, name = DocScanConstants.ID_DOCUMENT_TEXT_DATA_EXTRACTION),
        @JsonSubTypes.Type(value = RequestedSupplementaryDocTaskResponse.class, name = DocScanConstants.SUPPLEMENTARY_DOCUMENT_TEXT_DATA_EXTRACTION),
})
public abstract class RequestedTaskResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("state")
    private String state;

    /**
     * Returns the type of the {@link RequestedTaskResponse}
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the current state of the Requested Task
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

}
