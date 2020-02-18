package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.yoti.api.client.docs.DocScanConstants;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = SimpleCheckResponse.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleAuthenticityCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_AUTHENTICITY),
        @JsonSubTypes.Type(value = SimpleTextDataCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_TEXT_DATA_CHECK),
        @JsonSubTypes.Type(value = SimpleLivenessCheckResponse.class, name = DocScanConstants.LIVENESS),
        @JsonSubTypes.Type(value = SimpleFaceMatchCheckResponse.class, name = DocScanConstants.ID_DOCUMENT_FACE_MATCH)
})
public class SimpleCheckResponse implements CheckResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("state")
    private String state;

    @JsonProperty("resources_used")
    private List<String> resourcesUsed;

    @JsonProperty("generated_media")
    private List<SimpleGeneratedMedia> generatedMedia;

    @JsonProperty("report")
    private SimpleReportResponse report;

    @JsonProperty("created")
    private String created;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public List<String> getResourcesUsed() {
        return resourcesUsed;
    }

    @Override
    public List<? extends GeneratedMedia> getGeneratedMedia() {
        return generatedMedia;
    }

    @Override
    public ReportResponse getReport() {
        return report;
    }

    @Override
    public String getCreated() {
        return created;
    }

    @Override
    public String getLastUpdated() {
        return lastUpdated;
    }

}
