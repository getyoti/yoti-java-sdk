package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface SupplementaryDocumentTextExtractionTaskResponse extends TaskResponse {

    List<GeneratedSupplementaryDocumentTextDataCheckResponse> getGeneratedTextDataChecks();

}
