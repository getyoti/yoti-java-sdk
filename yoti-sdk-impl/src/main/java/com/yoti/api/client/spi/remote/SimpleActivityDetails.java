package com.yoti.api.client.spi.remote;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.ApplicationProfile;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ExtraData;

import java.util.Date;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;
import static org.bouncycastle.util.encoders.Base64.toBase64String;

final class SimpleActivityDetails implements ActivityDetails {

    private final String rememberMeId;
    private final String parentRememberMeId;
    private final ApplicationProfile applicationProfile;
    private final HumanProfile userProfile;
    private final Date timestamp;
    private final String receiptId;
    private final String base64Selfie;
    private final ExtraData extraData;

    // This will be removed in v3.0
    @Deprecated
    public SimpleActivityDetails(String rememberMeId, String parentRememberMeId, Profile userProfile, Profile applicationProfile, Date timestamp, byte[] receiptId) {
        this(rememberMeId, parentRememberMeId, userProfile, applicationProfile, new SimpleExtraData(), timestamp, receiptId);
    }

    public SimpleActivityDetails(String rememberMeId, String parentRememberMeId, Profile userProfile, Profile applicationProfile, ExtraData extraData, Date timestamp, byte[] receiptId) {
        this.rememberMeId = notNull(rememberMeId, "Remember Me id");
        this.parentRememberMeId = parentRememberMeId;
        this.userProfile = HumanProfileAdapter.wrap(notNull(userProfile, "User profile"));
        this.applicationProfile = ApplicationProfileAdapter.wrap(notNull(applicationProfile, "Application profile"));
        this.timestamp = notNull(timestamp, "Timestamp");
        this.receiptId = toBase64String(notNull(receiptId, "Receipt id"));

        if (this.userProfile.getSelfie() != null) {
            this.base64Selfie = "data:image/jpeg;base64," + toBase64String(this.userProfile.getSelfie().getValue().getContent());
        } else {
            this.base64Selfie = "";
        }

        this.extraData = notNull(extraData, "extraData");
    }

    @Override
    public HumanProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public ApplicationProfile getApplicationProfile() {
        return applicationProfile;
    }

    @Override
    @Deprecated
    public String getUserId() {
        return getRememberMeId();
    }

    @Override
    public String getRememberMeId() {
        return rememberMeId;
    }

    @Override
    public String getParentRememberMeId() {
        return parentRememberMeId;
    }

    @Override
    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    @Override
    public String getReceiptId() {
        return receiptId;
    }

    @Override
    public ExtraData getExtraData() { return extraData; }

    @Override
    @Deprecated
    public String getBase64Selfie() {
        return base64Selfie;
    }

}
