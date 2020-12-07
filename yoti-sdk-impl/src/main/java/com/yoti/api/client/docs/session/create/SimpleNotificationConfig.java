package com.yoti.api.client.docs.session.create;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleNotificationConfig implements NotificationConfig {

    @JsonProperty("auth_token")
    private final String authToken;

    @JsonProperty("auth_type")
    private final String authType;

    @JsonProperty("endpoint")
    private final String endpoint;

    @JsonProperty("topics")
    private final List<String> topics;

    SimpleNotificationConfig(String authToken, String authType, String endpoint, List<String> topics) {
        this.authToken = authToken;
        this.authType = authType;
        this.endpoint = endpoint;
        this.topics = topics;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public String getAuthType() {
        return authType;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public List<String> getTopics() {
        return topics;
    }

}
