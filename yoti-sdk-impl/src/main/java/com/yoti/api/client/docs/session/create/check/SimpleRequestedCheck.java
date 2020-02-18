package com.yoti.api.client.docs.session.create.check;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleRequestedCheck<T extends RequestedCheckConfig> implements RequestedCheck<T> {

    @JsonProperty("type")
    public abstract String getType();

    @JsonProperty("config")
    public abstract T getConfig();

}
