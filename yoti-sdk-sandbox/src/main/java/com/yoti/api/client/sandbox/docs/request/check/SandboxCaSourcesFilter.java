package com.yoti.api.client.sandbox.docs.request.check;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SandboxCaTypeListSourcesFilter.class, name = "TYPE_LIST"),
        @JsonSubTypes.Type(value = SandboxCaProfileSourcesFilter.class, name = "PROFILE")
})
public abstract class SandboxCaSourcesFilter {

}
