package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public class SupplementaryDocumentTextExtractionTaskResponse extends TaskResponse {

    public List<GeneratedSupplementaryDocumentTextDataCheckResponse> getGeneratedTextDataChecks() {
        return filterGeneratedChecksByType(GeneratedSupplementaryDocumentTextDataCheckResponse.class);
    }

}
