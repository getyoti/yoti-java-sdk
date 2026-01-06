package com.yoti.api.client.docs.session.retrieve;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @JsonProperty("applicant_profiles")
    private List<ApplicantProfileResourceResponse> applicantProfiles;

    @JsonProperty("share_codes")
    private List<ShareCodeResourceResponse> shareCodes;

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

    /**
     * Returns a filtered list of {@link LivenessResourceResponse} as {@link ZoomLivenessResourceResponse}
     *
     * @return the list of zoom liveness resources
     */
    public List<ZoomLivenessResourceResponse> getZoomLivenessResources() {
        return filterLivenessResourcesByType(ZoomLivenessResourceResponse.class);
    }

    /**
     * Returns a filtered list of {@link LivenessResourceResponse} as {@link StaticLivenessResourceResponse}
     *
     * @return the list of static liveness resources
     */
    public List<StaticLivenessResourceResponse> getStaticLivenessResources() {
        return filterLivenessResourcesByType(StaticLivenessResourceResponse.class);
    }

    private <T extends LivenessResourceResponse> List<T> filterLivenessResourcesByType(Class<T> clazz) {
        if (livenessCapture == null) {
            return Collections.emptyList();
        } else {
            return livenessCapture.stream()
                    .filter(clazz::isInstance)
                    .map(clazz::cast)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Returns ApplicantProfile resources uploaded by the user/relying business
     *
     * @return the list of applicant profile resources
     */
    public List<ApplicantProfileResourceResponse> getApplicantProfiles() {
        return applicantProfiles;
    }

    /**
     * Returns ShareCode resources uploaded by the user
     *
     * @return the list of Share Code resources
     */
    public List<ShareCodeResourceResponse> getShareCodes() {
        return shareCodes;
    }

    ResourceContainer filterForCheck(CheckResponse checkResponse) {
        ResourceContainer newResourceContainer = new ResourceContainer();
        newResourceContainer.idDocuments = filterResources(this.idDocuments, checkResponse.getResourcesUsed());
        newResourceContainer.supplementaryDocuments = filterResources(this.supplementaryDocuments, checkResponse.getResourcesUsed());
        newResourceContainer.livenessCapture = filterResources(this.livenessCapture, checkResponse.getResourcesUsed());
        newResourceContainer.faceCapture = filterResources(this.faceCapture, checkResponse.getResourcesUsed());
        newResourceContainer.applicantProfiles = filterResources(this.applicantProfiles, checkResponse.getResourcesUsed());
        return newResourceContainer;
    }

    private <T extends ResourceResponse> List<T> filterResources(List<T> resources, List<String> resourceIds) {
        if (resources == null) {
            return Collections.emptyList();
        }
        return resources.stream()
                .filter(resource -> resourceIds.contains(resource.getId()))
                .collect(Collectors.toList());
    }

}
