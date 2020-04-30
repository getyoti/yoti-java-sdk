package com.yoti.api.client.docs.session.retrieve;

import java.util.List;

public interface IdDocumentResourceResponse extends ResourceResponse {

    String getDocumentType();

    String getIssuingCountry();

    List<? extends PageResponse> getPages();

    DocumentFieldsResponse getDocumentFields();

    List<TextExtractionTaskResponse> getTextExtractionTasks();

}
