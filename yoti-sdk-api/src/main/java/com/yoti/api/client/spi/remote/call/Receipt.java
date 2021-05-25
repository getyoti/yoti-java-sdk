package com.yoti.api.client.spi.remote.call;

import static org.bouncycastle.util.encoders.Base64.toBase64String;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Receipt {
    @JsonProperty("receipt_id")
    private byte[] receiptId;
    @JsonProperty("other_party_profile_content")
    private byte[] otherPartyProfile;
    @JsonProperty("profile_content")
    private byte[] profile;
    @JsonProperty("other_party_extra_data_content")
    private byte[] otherPartyExtraData;
    @JsonProperty("extra_data_content")
    private byte[] extraData;
    @JsonProperty("wrapped_receipt_key")
    private byte[] wrappedReceiptKey;
    @JsonProperty("policy_uri")
    private String policyUri;
    @JsonProperty("personal_key")
    private byte[] personalKey;
    @JsonProperty("remember_me_id")
    private byte[] rememberMeId;
    @JsonProperty("parent_remember_me_id")
    private byte[] parentRememberMeId;
    @JsonProperty("sharing_outcome")
    private Outcome outcome;
    @JsonProperty("timestamp")
    private String timestamp;

    public static final class Builder {
        private final Receipt receipt = new Receipt();

        public Builder withWrappedReceiptKey(byte[] wrappedReceiptKey) {
            receipt.wrappedReceiptKey = wrappedReceiptKey;
            return this;
        }

        public Builder withProfile(byte[] profileData) {
            receipt.profile = profileData;
            return this;
        }

        public Builder withReceiptId(byte[] receiptId) {
            receipt.receiptId = receiptId;
            return this;
        }

        public Builder withOtherPartyProfile(byte[] otherPartyProfile) {
            receipt.otherPartyProfile = otherPartyProfile;
            return this;
        }

        public Builder withOtherPartyExtraData(byte[] otherPartyExtraData) {
            receipt.otherPartyExtraData = otherPartyExtraData;
            return this;
        }

        public Builder withExtraData(byte[] extraData) {
            receipt.extraData = extraData;
            return this;
        }

        public Builder withPolicyUri(String policyUri) {
            receipt.policyUri = policyUri;
            return this;
        }

        public Builder withPersonalKey(byte[] personalKey) {
            receipt.personalKey = personalKey;
            return this;
        }

        public Builder withOutcome(Outcome outcome) {
            receipt.outcome = outcome;
            return this;
        }

        public Builder withRememberMeId(byte[] rememberMeId) {
            receipt.rememberMeId = rememberMeId;
            return this;
        }

        public Builder withParentRememberMeId(byte[] parentRememberMeId) {
            receipt.parentRememberMeId = parentRememberMeId;
            return this;
        }

        public Builder withTimestamp(String timestamp) {
            receipt.timestamp = timestamp;
            return this;
        }

        public Receipt build() {
            return receipt;
        }
    }

    public enum Outcome {
        SUCCESS(true), FAILURE(false);

        private final boolean successful;

        Outcome(boolean successful) {
            this.successful = successful;
        }

        public boolean isSuccessful() {
            return successful;
        }
    }

    public byte[] getReceiptId() {
        return receiptId;
    }

    public byte[] getOtherPartyProfile() {
        return otherPartyProfile;
    }

    public byte[] getProfile() {
        return profile;
    }

    public byte[] getOtherPartyExtraData() {
        return otherPartyExtraData;
    }

    public byte[] getExtraData() {
        return extraData;
    }

    public byte[] getWrappedReceiptKey() {
        return wrappedReceiptKey;
    }

    public String getPolicyUri() {
        return policyUri;
    }

    public byte[] getPersonalKey() {
        return personalKey;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public byte[] getRememberMeId() {
        return rememberMeId;
    }

    public byte[] getParentRememberMeId() {
        return parentRememberMeId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(extraData);
        result = prime * result + Arrays.hashCode(otherPartyExtraData);
        result = prime * result + Arrays.hashCode(otherPartyProfile);
        result = prime * result + ((outcome == null) ? 0 : outcome.hashCode());
        result = prime * result + Arrays.hashCode(personalKey);
        result = prime * result + ((policyUri == null) ? 0 : policyUri.hashCode());
        result = prime * result + Arrays.hashCode(profile);
        result = prime * result + Arrays.hashCode(receiptId);
        result = prime * result + Arrays.hashCode(rememberMeId);
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + Arrays.hashCode(wrappedReceiptKey);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Receipt other = (Receipt) obj;
        if (!Arrays.equals(extraData, other.extraData)) {
            return false;
        }
        if (!Arrays.equals(otherPartyExtraData, other.otherPartyExtraData)) {
            return false;
        }
        if (!Arrays.equals(otherPartyProfile, other.otherPartyProfile)) {
            return false;
        }
        if (outcome != other.outcome) {
            return false;
        }
        if (!Arrays.equals(personalKey, other.personalKey)) {
            return false;
        }
        if (policyUri == null) {
            if (other.policyUri != null) {
                return false;
            }
        } else if (!policyUri.equals(other.policyUri)) {
            return false;
        }
        if (!Arrays.equals(profile, other.profile)) {
            return false;
        }
        if (!Arrays.equals(receiptId, other.receiptId)) {
            return false;
        }
        if (!Arrays.equals(rememberMeId, other.rememberMeId)) {
            return false;
        }
        if (timestamp == null) {
            if (other.timestamp != null) {
                return false;
            }
        } else if (!timestamp.equals(other.timestamp)) {
            return false;
        }
        if (!Arrays.equals(wrappedReceiptKey, other.wrappedReceiptKey)) {
            return false;
        }
        return true;
    }

    public String getDisplayReceiptId() {
        return receiptId != null ? toBase64String(receiptId) : "<>";
    }
}
