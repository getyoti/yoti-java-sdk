package com.yoti.api.client.docs.session.retrieve;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ResourceResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("tasks")
    private List<TaskResponse> tasks;

    public String getId() {
        return id;
    }

    public List<? extends TaskResponse> getTasks() {
        return tasks;
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
