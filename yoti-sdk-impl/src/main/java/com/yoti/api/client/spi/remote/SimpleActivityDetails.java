package com.yoti.api.client.spi.remote;

import static org.bouncycastle.util.encoders.Base64.toBase64String;

import java.util.Date;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.ApplicationProfile;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Profile;

final class SimpleActivityDetails implements ActivityDetails {
    private final String userId;
    private final ApplicationProfile applicationProfile;
    private final HumanProfile userProfile;
    private final Date timestamp;
    private final String receiptId;
    private final String base64Selfie;

    public SimpleActivityDetails(String userId, Profile userProfile, Profile applicationProfile, Date timestamp,
            byte[] receiptId) {
        this.userId = notNull(userId, "User id");
        this.userProfile = HumanProfileAdapter.wrap(notNull(userProfile, "User profile"));
        this.applicationProfile = ApplicationProfileAdapter.wrap(notNull(applicationProfile, "Application profile"));
        this.timestamp = notNull(timestamp, "Timestamp");
        this.receiptId = toBase64String(notNull(receiptId, "Receipt id"));
        
        if(this.userProfile.getSelfie() != null) {
        		this.base64Selfie = "data:image/jpeg;base64," + toBase64String(this.userProfile.getSelfie().getContent());
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
    public String getUserId() {
        return userId;
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

    private <T> T notNull(T value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is null");
        }
        return value;
    }
}
