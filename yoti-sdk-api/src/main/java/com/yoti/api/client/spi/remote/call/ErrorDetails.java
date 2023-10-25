package com.yoti.api.client.spi.remote.call;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = ErrorDetails.Builder.class)
public final class ErrorDetails implements Serializable {

    private static final long serialVersionUID = -6429196723990930305L;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final String code;
    private final String description;
    private final String reason;

    private ErrorDetails(Builder builder) {
        this.code = builder.code;
        this.description = builder.description;
        this.reason = builder.reason;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ErrorDetails that = (ErrorDetails) o;
        return Objects.equals(code, that.code) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description);
    }

    @Override
    public String toString() {
        return String.format("Error[code='%s', description='%s', reason='%s']", code, description, reason);
    }

    public static final class Builder {

        private String code;
        private String description;
        private String reason;

        private Builder() { }

        @JsonProperty(Property.ERROR_CODE)
        public Builder code(String code) {
            this.code = code;
            return this;
        }

        @JsonProperty(Property.DESCRIPTION)
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        @JsonProperty(Property.REASON)
        public Builder reason(Map<String, Object> map) {
            try {
                reason = MAPPER.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                throw new ErrorDetailsException(
                        String.format("Failed to parse failure receipt error reason: '%s'", e.getMessage())
                );
            }
            return this;
        }

        public ErrorDetails build() {
            return new ErrorDetails(this);
        }

    }

    private static final class Property {

        private static final String ERROR_CODE = "error_code";
        private static final String DESCRIPTION = "description";
        private static final String REASON = "error_reason";

        private Property() { }

    }

}
