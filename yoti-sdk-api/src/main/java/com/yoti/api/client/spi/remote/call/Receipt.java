package com.yoti.api.client.spi.remote.call;

import static org.bouncycastle.util.encoders.Base64.toBase64String;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Receipt.Builder.class)
public final class Receipt {

    @JsonProperty(Property.RECEIPT_ID)
    private final byte[] receiptId;

    @JsonProperty(Property.OTHER_PARTY_PROFILE_CONTENT)
    private final byte[] otherPartyProfile;

    @JsonProperty(Property.PROFILE_CONTENT)
    private final byte[] profile;

    @JsonProperty(Property.OTHER_PARTY_EXTRA_DATA_CONTENT)
    private final byte[] otherPartyExtraData;

    @JsonProperty(Property.EXTRA_DATA_CONTENT)
    private final byte[] extraData;

    @JsonProperty(Property.WRAPPED_RECEIPT_KEY)
    private final byte[] wrappedReceiptKey;

    @JsonProperty(Property.POLICY_URI)
    private final String policyUri;

    @JsonProperty(Property.PERSONAL_KEY)
    private final byte[] personalKey;

    @JsonProperty(Property.REMEMBER_ME_ID)
    private final byte[] rememberMeId;

    @JsonProperty(Property.PARENT_REMEMBER_ME_ID)
    private final byte[] parentRememberMeId;

    @JsonProperty(Property.SHARING_OUTCOME)
    private final Outcome outcome;

    @JsonProperty(Property.TIMESTAMP)
    private final String timestamp;

    public Receipt(Builder builder) {
        this.receiptId = builder.receiptId;
        this.otherPartyProfile = builder.otherPartyProfile;
        this.profile = builder.profile;
        this.otherPartyExtraData = builder.otherPartyExtraData;
        this.extraData = builder.extraData;
        this.wrappedReceiptKey = builder.wrappedReceiptKey;
        this.policyUri = builder.policyUri;
        this.personalKey = builder.personalKey;
        this.rememberMeId = builder.rememberMeId;
        this.parentRememberMeId = builder.parentRememberMeId;
        this.outcome = builder.outcome;
        this.timestamp = builder.timestamp;
    }

    public byte[] getReceiptId() {
        return Optional.ofNullable(receiptId).map(byte[]::clone).orElse(null);
    }

    public byte[] getOtherPartyProfile() {
        return Optional.ofNullable(otherPartyProfile).map(byte[]::clone).orElse(null);
    }

    public byte[] getProfile() {
        return Optional.ofNullable(profile).map(byte[]::clone).orElse(null);
    }

    public byte[] getOtherPartyExtraData() {
        return Optional.ofNullable(otherPartyExtraData).map(byte[]::clone).orElse(null);
    }

    public byte[] getExtraData() {
        return Optional.ofNullable(extraData).map(byte[]::clone).orElse(null);
    }

    public byte[] getWrappedReceiptKey() {
        return Optional.ofNullable(wrappedReceiptKey).map(byte[]::clone).orElse(null);
    }

    public String getPolicyUri() {
        return policyUri;
    }

    public byte[] getPersonalKey() {
        return Optional.ofNullable(personalKey).map(byte[]::clone).orElse(null);
    }

    public byte[] getRememberMeId() {
        return Optional.ofNullable(rememberMeId).map(byte[]::clone).orElse(null);
    }

    public byte[] getParentRememberMeId() {
        return Optional.ofNullable(parentRememberMeId).map(byte[]::clone).orElse(null);
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean hasOutcome(Outcome other) {
        return other == outcome;
    }

    public String getDisplayReceiptId() {
        return receiptId != null ? toBase64String(receiptId) : "<>";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Receipt receipt = (Receipt) o;
        return Arrays.equals(receiptId, receipt.receiptId)
                && Arrays.equals(otherPartyProfile, receipt.otherPartyProfile)
                && Arrays.equals(profile, receipt.profile)
                && Arrays.equals(otherPartyExtraData, receipt.otherPartyExtraData)
                && Arrays.equals(extraData, receipt.extraData)
                && Arrays.equals(wrappedReceiptKey, receipt.wrappedReceiptKey)
                && Objects.equals(policyUri, receipt.policyUri)
                && Arrays.equals(personalKey, receipt.personalKey)
                && Arrays.equals(rememberMeId, receipt.rememberMeId)
                && Arrays.equals(parentRememberMeId, receipt.parentRememberMeId)
                && outcome == receipt.outcome
                && Objects.equals(timestamp, receipt.timestamp);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(policyUri, outcome, timestamp);
        result = 31 * result + Arrays.hashCode(receiptId);
        result = 31 * result + Arrays.hashCode(otherPartyProfile);
        result = 31 * result + Arrays.hashCode(profile);
        result = 31 * result + Arrays.hashCode(otherPartyExtraData);
        result = 31 * result + Arrays.hashCode(extraData);
        result = 31 * result + Arrays.hashCode(wrappedReceiptKey);
        result = 31 * result + Arrays.hashCode(personalKey);
        result = 31 * result + Arrays.hashCode(rememberMeId);
        result = 31 * result + Arrays.hashCode(parentRememberMeId);
        return result;
    }

    public static final class Builder {

        @JsonProperty(Property.RECEIPT_ID)
        private byte[] receiptId;

        @JsonProperty(Property.OTHER_PARTY_PROFILE_CONTENT)
        private byte[] otherPartyProfile;

        @JsonProperty(Property.PROFILE_CONTENT)
        private byte[] profile;

        @JsonProperty(Property.OTHER_PARTY_EXTRA_DATA_CONTENT)
        private byte[] otherPartyExtraData;

        @JsonProperty(Property.EXTRA_DATA_CONTENT)
        private byte[] extraData;

        @JsonProperty(Property.WRAPPED_RECEIPT_KEY)
        private byte[] wrappedReceiptKey;

        @JsonProperty(Property.POLICY_URI)
        private String policyUri;

        @JsonProperty(Property.PERSONAL_KEY)
        private byte[] personalKey;

        @JsonProperty(Property.REMEMBER_ME_ID)
        private byte[] rememberMeId;

        @JsonProperty(Property.PARENT_REMEMBER_ME_ID)
        private byte[] parentRememberMeId;

        @JsonProperty(Property.SHARING_OUTCOME)
        private Outcome outcome;

        @JsonProperty(Property.TIMESTAMP)
        private String timestamp;

        public Builder withReceiptId(byte[] receiptId) {
            this.receiptId = Optional.ofNullable(receiptId).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withOtherPartyProfile(byte[] otherPartyProfile) {
            this.otherPartyProfile = Optional.ofNullable(otherPartyProfile).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withProfile(byte[] profile) {
            this.profile = Optional.ofNullable(profile).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withOtherPartyExtraData(byte[] otherPartyExtraData) {
            this.otherPartyExtraData = Optional.ofNullable(otherPartyExtraData).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withExtraData(byte[] extraData) {
            this.extraData = Optional.ofNullable(extraData).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withWrappedReceiptKey(byte[] wrappedReceiptKey) {
            this.wrappedReceiptKey = Optional.ofNullable(wrappedReceiptKey).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withPolicyUri(String policyUri) {
            this.policyUri = policyUri;
            return this;
        }

        public Builder withPersonalKey(byte[] personalKey) {
            this.personalKey = Optional.ofNullable(personalKey).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withRememberMeId(byte[] rememberMeId) {
            this.rememberMeId = Optional.ofNullable(rememberMeId).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withParentRememberMeId(byte[] parentRememberMeId) {
            this.parentRememberMeId = Optional.ofNullable(parentRememberMeId).map(byte[]::clone).orElse(null);
            return this;
        }

        public Builder withOutcome(Outcome outcome) {
            this.outcome = outcome;
            return this;
        }

        public Builder withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Receipt build() {
            return new Receipt(this);
        }
    }

    public enum Outcome {
        SUCCESS(true),
        FAILURE(false);

        private final boolean successful;

        Outcome(boolean successful) {
            this.successful = successful;
        }

        public boolean isSuccessful() {
            return successful;
        }
    }
    
    private static class Property {
     
        private static final String RECEIPT_ID = "receipt_id";
        private static final String OTHER_PARTY_PROFILE_CONTENT = "other_party_profile_content";
        private static final String PROFILE_CONTENT = "profile_content";
        private static final String OTHER_PARTY_EXTRA_DATA_CONTENT = "other_party_extra_data_content";
        private static final String EXTRA_DATA_CONTENT = "extra_data_content";
        private static final String WRAPPED_RECEIPT_KEY = "wrapped_receipt_key";
        private static final String POLICY_URI = "policy_uri";
        private static final String PERSONAL_KEY = "personal_key";
        private static final String REMEMBER_ME_ID = "remember_me_id";
        private static final String PARENT_REMEMBER_ME_ID = "parent_remember_me_id";
        private static final String SHARING_OUTCOME = "sharing_outcome";
        private static final String TIMESTAMP = "timestamp";

        private Property() { }

    }
    
}
