package com.yoti.api.client.spi.remote.call.identity;

import java.time.OffsetDateTime;
import java.util.Optional;

import com.yoti.api.client.ApplicationProfile;
import com.yoti.api.client.ExtraData;
import com.yoti.api.client.HumanProfile;

public final class Receipt {

    private final String id;
    private final String sessionId;
    private final String rememberMeId;
    private final String parentRememberMeId;
    private final OffsetDateTime timestamp;
    private final ApplicationContent applicationContent;
    private final UserContent userContent;
    private final String error;
    private final String errorReason;

    private Receipt(Builder builder) {
        id = builder.id;
        sessionId = builder.sessionId;
        rememberMeId = builder.rememberMeId;
        parentRememberMeId = builder.parentRememberMeId;
        timestamp = builder.timestamp;
        applicationContent = builder.applicationContent;
        userContent = builder.userContent;
        error = builder.error;
        errorReason = builder.errorReason;
    }

    public String getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getRememberMeId() {
        return rememberMeId;
    }

    public String getParentRememberMeId() {
        return parentRememberMeId;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public Optional<HumanProfile> getProfile() {
        return userContent.getProfile();
    }

    public Optional<ExtraData> getExtraData() {
        return userContent.getExtraData();
    }

    public ApplicationContent getApplicationContent() {
        return applicationContent;
    }

    public UserContent getUserContent() {
        return userContent;
    }

    public Optional<String> getError() {
        return Optional.ofNullable(error);
    }

    public Optional<String> getErrorReason() {
        return Optional.ofNullable(errorReason);
    }

    public static Builder forReceipt(String id) {
        return new Builder(id);
    }

    public static final class Builder {

        private final String id;

        private String sessionId;
        private String rememberMeId;
        private String parentRememberMeId;
        private OffsetDateTime timestamp;
        private ApplicationContent applicationContent;
        private UserContent userContent;
        private String error;
        private String errorReason;

        private Builder(String id) {
            this.id = id;
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder rememberMeId(String rememberMeId) {
            this.rememberMeId = rememberMeId;
            return this;
        }

        public Builder parentRememberMeId(String parentRememberMeId) {
            this.parentRememberMeId = parentRememberMeId;
            return this;
        }

        public Builder timestamp(OffsetDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder applicationContent(ApplicationProfile profile, ExtraData extraData) {
            this.applicationContent = new ApplicationContent(profile, extraData);
            return this;
        }

        public Builder userContent(HumanProfile profile, ExtraData extraData) {
            this.userContent = new UserContent(profile, extraData);
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder errorReason(String reason) {
            errorReason = reason;
            return this;
        }

        public Receipt build() {
            return new Receipt(this);
        }

    }

    public static class ApplicationContent {

        private final ApplicationProfile profile;
        private final ExtraData extraData;

        private ApplicationContent(ApplicationProfile profile, ExtraData extraData) {
            this.profile = profile;
            this.extraData = extraData;
        }

        public ApplicationProfile getProfile() {
            return profile;
        }

        public Optional<ExtraData> getExtraData() {
            return Optional.ofNullable(extraData);
        }

    }

    public static class UserContent {

        private final HumanProfile profile;
        private final ExtraData extraData;

        private UserContent(HumanProfile profile, ExtraData extraData) {
            this.profile = profile;
            this.extraData = extraData;
        }

        public Optional<HumanProfile> getProfile() {
            return Optional.ofNullable(profile);
        }

        public Optional<ExtraData> getExtraData() {
            return Optional.ofNullable(extraData);
        }

    }

}
