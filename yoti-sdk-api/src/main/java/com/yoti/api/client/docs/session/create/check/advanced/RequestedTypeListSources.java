package com.yoti.api.client.docs.session.create.check.advanced;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestedTypeListSources extends RequestedCaSources {

    @JsonProperty("types")
    private final List<String> types;

    private RequestedTypeListSources(List<String> types) {
        this.types = types;
    }

    public static RequestedTypeListSources.Builder builder() {
        return new RequestedTypeListSources.Builder();
    }

    public List<String> getTypes() {
        return types;
    }

    public static class Builder {

        private List<String> types;

        private Builder() {}

        public Builder withTypes(List<String> types) {
            this.types = types;
            return this;
        }

        public RequestedTypeListSources build() {
            return new RequestedTypeListSources(types);
        }
    }
}
