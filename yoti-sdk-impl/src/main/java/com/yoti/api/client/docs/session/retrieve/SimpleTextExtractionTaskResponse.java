package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleTextExtractionTaskResponse extends SimpleTaskResponse implements TextExtractionTaskResponse {

}
