package com.yoti.api.client.docs.session.retrieve;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.DocScanConstants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = TaskResponse.class, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = IdDocTextExtractionTaskResponse.class, name = DocScanConstants.ID_DOCUMENT_TEXT_DATA_EXTRACTION),
        @JsonSubTypes.Type(value = SupplementaryDocumentTextExtractionTaskResponse.class, name = DocScanConstants.SUPPLEMENTARY_DOCUMENT_TEXT_DATA_EXTRACTION),
})
public class TaskResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("state")
    private String state;

    @JsonProperty("created")
    private String created;

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("generated_checks")
    private List<GeneratedCheckResponse> generatedChecks;

    @JsonProperty("generated_media")
    private List<GeneratedMedia> generatedMedia;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public String getCreated() {
        return created;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public List<? extends GeneratedCheckResponse> getGeneratedChecks() {
        return generatedChecks;
    }

    public List<? extends GeneratedMedia> getGeneratedMedia() {
        return generatedMedia;
    }

    protected <T extends GeneratedCheckResponse> List<T> filterGeneratedChecksByType(Class<T> clazz) {
        List<T> filteredList = new ArrayList<>();
        for (GeneratedCheckResponse generatedCheckResponse : generatedChecks) {
            if (clazz.isInstance(generatedCheckResponse)) {
                filteredList.add(clazz.cast(generatedCheckResponse));
            }
        }
        return filteredList;
    }

}
