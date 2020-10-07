package com.yoti.api.client.docs.session.retrieve;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleResourceContainer implements ResourceContainer {

    @JsonProperty("id_documents")
    private List<SimpleIdDocumentResourceResponse> idDocuments;

    @JsonProperty("supplementary_documents")
    private List<SimpleSupplementaryDocumentResourceResponse> supplementaryDocuments;

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

    @Override
    public List<? extends SupplementaryDocumentResourceResponse> getSupplementaryDocuments() {
        return supplementaryDocuments;
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

    @Override
    public List<ZoomLivenessResourceResponse> getZoomLivenessResources() {
        return filterLivenessResourcesByType(ZoomLivenessResourceResponse.class);
    }

    private <T extends LivenessResourceResponse> List<T> filterLivenessResourcesByType(Class<T> clazz) {
        List<T> filteredList = new ArrayList<>();
        for (LivenessResourceResponse livenessResourceResponse : livenessCapture) {
            if (clazz.isInstance(livenessResourceResponse)) {
                filteredList.add(clazz.cast(livenessResourceResponse));
            }
        }
        return filteredList;
    }

}
