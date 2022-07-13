package com.yoti.api.client.spi.remote.call;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = ErrorDetails.Builder.class)
public final class ErrorDetails implements Serializable {

    private static final long serialVersionUID = -6429196723990930305L;

    private final String code;
    private final String description;

    private ErrorDetails(Builder builder) {
        this.code = builder.code;
        this.description = builder.description;
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
        return String.format("Error[code='%s', description='%s']", code, description);
    }

    public static final class Builder {

        private String code;
        private String description;

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

        public ErrorDetails build() {
            return new ErrorDetails(this);
        }

    }

    private static final class Property {

        private static final String ERROR_CODE = "error_code";
        private static final String DESCRIPTION = "description";

        private Property() { }

    }

}
