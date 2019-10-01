package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleResourceContainer implements ResourceContainer {

    @JsonProperty("id_documents")
    private List<SimpleIdDocumentResourceResponse> idDocuments;

    @JsonProperty("liveness_capture")
    private List<SimpleLivenessResourceResponse> livenessCapture;

    /**
     * Returns ID documents that were uploaded by the user
     *
     * @return the list of documents
     */
    @Override
    public List<? extends IdDocumentResourceResponse> getIdDocuments() {
        return idDocuments;
    }

    /**
     * Returns liveness resources uploaded by the user
     *
     * @return the list of liveness resources
     */
    @Override
    public List<? extends LivenessResourceResponse> getLivenessCapture() {
        return livenessCapture;
    }

}
