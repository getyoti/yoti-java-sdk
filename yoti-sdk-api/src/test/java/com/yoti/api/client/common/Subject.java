package com.yoti.api.client.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Subject {

    @JsonProperty("subject_id")
    private final String id;

    public Subject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
