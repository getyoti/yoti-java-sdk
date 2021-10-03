package com.yoti.api.client.docs.session.retrieve;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceContainer {

    @JsonProperty("id_documents")
    private List<IdDocumentResourceResponse> idDocuments;

    @JsonProperty("supplementary_documents")
    private List<SupplementaryDocumentResourceResponse> supplementaryDocuments;

    @JsonProperty("liveness_capture")
    private List<LivenessResourceResponse> livenessCapture;

    @JsonProperty("face_capture")
    private List<FaceCaptureResourceResponse> faceCapture;

    /**
     * Returns ID documents that were uploaded by the user
     *
     * @return the list of documents
     */
    public List<? extends IdDocumentResourceResponse> getIdDocuments() {
        return idDocuments;
    }

    /**
     * Returns supplementary documents that were uploaded by the user
     *
     * @return the list of supplementary documents
     */
    public List<? extends SupplementaryDocumentResourceResponse> getSupplementaryDocuments() {
        return supplementaryDocuments;
    }

    /**
     * Returns liveness resources uploaded by the user
     *
     * @return the list of liveness resources
     */
    public List<? extends LivenessResourceResponse> getLivenessCapture() {
        return livenessCapture;
    }

    /**
     * Returns Face Captures resources uploaded by the user/relying business
     *
     * @return the list of face capture resources
     */
    public List<? extends FaceCaptureResourceResponse> getFaceCapture() {
        return faceCapture;
    }

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
