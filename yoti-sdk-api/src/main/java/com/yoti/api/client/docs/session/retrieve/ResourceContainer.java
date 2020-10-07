package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface ResourceContainer {

    List<? extends IdDocumentResourceResponse> getIdDocuments();

    List<? extends SupplementaryDocumentResourceResponse> getSupplementaryDocuments();

    List<? extends LivenessResourceResponse> getLivenessCapture();

    List<ZoomLivenessResourceResponse> getZoomLivenessResources();

}
