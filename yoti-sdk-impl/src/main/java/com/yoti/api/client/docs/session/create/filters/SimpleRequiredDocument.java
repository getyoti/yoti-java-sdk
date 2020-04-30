package com.yoti.api.client.docs.session.create.filters;

import com.fasterxml.jackson.annotation.JsonProperty;

abstract class SimpleRequiredDocument implements RequiredDocument {

    @JsonProperty("type")
    public abstract String getType();

}
