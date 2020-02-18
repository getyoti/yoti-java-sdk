package com.yoti.api.client.docs.session.create.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleRequestedTask<T extends RequestedTaskConfig> implements RequestedTask<T> {

    @JsonProperty("type")
    @Override
    public abstract String getType();

    @JsonProperty("config")
    @Override
    public abstract T getConfig();

}
