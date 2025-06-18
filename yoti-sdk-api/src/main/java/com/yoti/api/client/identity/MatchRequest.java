package com.yoti.api.client.identity;

import com.yoti.validation.Validation;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class MatchRequest {

    @JsonProperty(Property.VALUE)
    private final String value;

    @JsonProperty(Property.NOTIFICATION)
    private final MatchNotification notification;

    private MatchRequest(Builder builder) {
        value = builder.value;
        notification = builder.notification;
    }

    public String getValue() {
        return value;
    }

    public MatchNotification getNotification() {
        return notification;
    }

    public static Builder builder(String value) {
        return new Builder(value);
    }

    public static final class Builder {

        private final String value;

        private MatchNotification notification;

        private Builder(String value) {
            Validation.notNullOrEmpty(value, Property.VALUE);

            this.value = value;
        }

        public Builder withNotification(MatchNotification notification) {
            this.notification = notification;
            return this;
        }

        public MatchRequest build() {
            return new MatchRequest(this);
        }

    }

    private static final class Property {

        private static final String VALUE = "value";
        private static final String NOTIFICATION = "notification";

    }

}
