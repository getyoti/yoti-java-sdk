package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import static org.bouncycastle.util.encoders.Base64.toBase64String;

import java.util.Date;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.ApplicationProfile;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Profile;

final class SimpleActivityDetails implements ActivityDetails {

    private final String rememberMeId;
    private final ApplicationProfile applicationProfile;
    private final HumanProfile userProfile;
    private final Date timestamp;
    private final String receiptId;
    private final String base64Selfie;

    public SimpleActivityDetails(String rememberMeId, Profile userProfile, Profile applicationProfile, Date timestamp, byte[] receiptId) {
        this.rememberMeId = notNull(rememberMeId, "Remember Me id");
        this.userProfile = HumanProfileAdapter.wrap(notNull(userProfile, "User profile"));
        this.applicationProfile = ApplicationProfileAdapter.wrap(notNull(applicationProfile, "Application profile"));
        this.timestamp = notNull(timestamp, "Timestamp");
        this.receiptId = toBase64String(notNull(receiptId, "Receipt id"));
        
        if(this.userProfile.getSelfie() != null) {
            this.base64Selfie = "data:image/jpeg;base64," + toBase64String(this.userProfile.getSelfie().getValue().getContent());
        } else {
            this.base64Selfie = "";
        }
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
    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    @Override
    public String getReceiptId() {
        return receiptId;
    }
    
    @Override
    public String getBase64Selfie() {
        return base64Selfie;
    }

}
