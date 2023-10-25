package com.yoti.api.client.spi.remote.call.identity;

import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = WrappedReceipt.Builder.class)
public final class WrappedReceipt {

    private final String id;
    private final String sessionId;
    private final OffsetDateTime timestamp;
    private final Content content;
    private final Content otherPartyContent;
    private final String wrappedItemKeyId;
    private final byte[] rememberMeId;
    private final byte[] parentRememberMeId;
    private final byte[] wrappedKey;
    private final String error;
    private final String errorReason;

    private WrappedReceipt(Builder builder) {
        id = builder.id;
        sessionId = builder.sessionId;
        timestamp = builder.timestamp;
        content = builder.content;
        otherPartyContent = builder.otherPartyContent;
        wrappedItemKeyId = builder.wrappedItemKeyId;
        rememberMeId = builder.rememberMeId;
        parentRememberMeId = builder.parentRememberMeId;
        wrappedKey = builder.wrappedKey;
        error = builder.error;
        errorReason = builder.errorReason;
    }

    public String getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public byte[] getProfile() {
        return content.profile()
                .orElseThrow(() -> new DigitalIdentityException("Application profile should not be missing"));
    }

    public Optional<byte[]> getExtraData() {
        return content.extraData();
    }

    public Optional<byte[]> getOtherPartyProfile() {
        return Optional.ofNullable(otherPartyContent).flatMap(Content::profile);
    }

    public Optional<byte[]> getOtherPartyExtraData() {
        return Optional.ofNullable(otherPartyContent).flatMap(Content::extraData);
    }

    public String getWrappedItemKeyId() {
        return wrappedItemKeyId;
    }

    public Optional<byte[]> getRememberMeId() {
        return Optional.ofNullable(rememberMeId).map(byte[]::clone);
    }

    public Optional<byte[]> getParentRememberMeId() {
        return Optional.ofNullable(parentRememberMeId).map(byte[]::clone);
    }

    public byte[] getWrappedKey() {
        return wrappedKey.clone();
    }

    public String getError() {
        return error;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public static final class Builder {

        private String id;
        private String sessionId;
        private OffsetDateTime timestamp;
        private Content content;
        private Content otherPartyContent;
        private String wrappedItemKeyId;
        private byte[] rememberMeId;
        private byte[] parentRememberMeId;
        private byte[] wrappedKey;
        private String error;
        private String errorReason;

        private Builder() { }

        @JsonProperty(Property.ID)
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        @JsonProperty(Property.SESSION_ID)
        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        @JsonProperty(Property.TIMESTAMP)
        public Builder timestamp(String timestamp) {
            this.timestamp = OffsetDateTime.parse(timestamp);
            return this;
        }

        @JsonProperty(Property.CONTENT)
        public Builder content(Content content) {
            this.content = content;
            return this;
        }

        @JsonProperty(Property.OTHER_PARTY_CONTENT)
        public Builder otherPartyContent(Content otherPartyContent) {
            this.otherPartyContent = otherPartyContent;
            return this;
        }

        @JsonProperty(Property.WRAPPED_ITEM_KEY_ID)
        public Builder wrappedItemKeyId(String wrappedItemKeyId) {
            this.wrappedItemKeyId = wrappedItemKeyId;
            return this;
        }

        @JsonProperty(Property.REMEMBER_ME_ID)
        public Builder rememberMeId(String rememberMeId) {
            this.rememberMeId = decode(rememberMeId);
            return this;
        }

        @JsonProperty(Property.PARENT_REMEMBER_ME_ID)
        public Builder parentRememberMeId(String parentRememberMeId) {
            this.parentRememberMeId = decode(parentRememberMeId);
            return this;
        }

        @JsonProperty(Property.WRAPPED_KEY)
        public Builder wrappedKey(String wrappedKey) {
            this.wrappedKey = decode(wrappedKey);
            return this;
        }

        @JsonProperty(Property.ERROR)
        public Builder error(String error) {
            this.error = error;
            return this;
        }

        @JsonProperty(Property.ERROR_REASON)
        public Builder errorReason(Map<String, Object> reason) {
            try {
                this.errorReason = new ObjectMapper().writeValueAsString(reason);
            } catch (JsonProcessingException e) {
                throw new DigitalIdentityException("The reason of the failed share has an unexpected format");
            }
            return this;
        }

        public WrappedReceipt build() {
            return new WrappedReceipt(this);
        }

        private static byte[] decode(String value) {
            return Base64.getDecoder().decode(value);
        }

    }

    private static class Content {

        private byte[] profile;
        private byte[] extraData;

        public Optional<byte[]> profile() {
            return Optional.ofNullable(profile).map(byte[]::clone);
        }

        public Optional<byte[]> extraData() {
            return Optional.ofNullable(extraData).map(byte[]::clone);
        }

        @JsonProperty(Property.Content.PROFILE)
        public void setProfile(String profile) {
            this.profile = decode(profile);
        }

        @JsonProperty(Property.Content.EXTRA_DATA)
        public void setExtraData(String extraData) {
            this.extraData = decode(extraData);
        }

        private static byte[] decode(String value) {
            return Base64.getDecoder().decode(value);
        }

    }

    private static class Property {

        private static final String ID = "id";
        private static final String SESSION_ID = "sessionId";
        private static final String TIMESTAMP = "timestamp";
        private static final String REMEMBER_ME_ID = "rememberMeId";
        private static final String PARENT_REMEMBER_ME_ID = "parentRememberMeId";
        private static final String CONTENT = "content";
        private static final String OTHER_PARTY_CONTENT = "otherPartyContent";
        private static final String WRAPPED_ITEM_KEY_ID = "wrappedItemKeyId";
        private static final String WRAPPED_KEY = "wrappedKey";
        private static final String ERROR = "error";
        private static final String ERROR_REASON = "errorReason";

        private static class Content {

            private static final String PROFILE = "profile";
            private static final String EXTRA_DATA = "extraData";

        }

    }

}
