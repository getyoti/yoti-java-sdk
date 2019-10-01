package com.yoti.api.client.docs.session.retrieve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SimpleResourceResponse implements ResourceResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tasks")
    private List<SimpleTaskResponse> tasks;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<? extends TaskResponse> getTasks() {
        return tasks;
    }

}
