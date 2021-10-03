package com.yoti.api.client.docs.session.retrieve;

import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.docs.session.retrieve.configuration.capture.source.AllowedSourceResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ResourceResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tasks")
    private List<TaskResponse> tasks;

    @JsonProperty("source")
    private AllowedSourceResponse source;

    public String getId() {
        return id;
    }

    public List<? extends TaskResponse> getTasks() {
        return tasks;
    }

    public AllowedSourceResponse getSource() {
        return source;
    }

    protected <T extends TaskResponse> List<T> filterTasksByType(Class<T> clazz) {
        List<T> filteredList = new ArrayList<>();
        for (TaskResponse taskResponse : tasks) {
            if (clazz.isInstance(taskResponse)) {
                filteredList.add(clazz.cast(taskResponse));
            }
        }
        return filteredList;
    }

}
