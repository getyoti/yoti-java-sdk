package com.yoti.api.client;

import static com.yoti.validation.Validation.notNull;

import static org.bouncycastle.util.encoders.Base64.toBase64String;

import java.util.Date;

/**
 * Details of an activity between a user and the application.
 */
public final class ActivityDetails {

    private final String rememberMeId;
    private final String parentRememberMeId;
    private final ApplicationProfile thisPartyProfile;
    private final HumanProfile otherPartyProfile;
    private final Date timestamp;
    private final String receiptId;
    private final ExtraData extraData;

    public ActivityDetails(String rememberMeId,
            String parentRememberMeId,
            HumanProfile otherPartyProfile,
            ApplicationProfile thisPartyProfile,
            ExtraData extraData,
            Date timestamp,
            byte[] receiptId) {
        this.rememberMeId = rememberMeId;
        this.parentRememberMeId = parentRememberMeId;
        this.otherPartyProfile = notNull(otherPartyProfile, "User profile");
        this.thisPartyProfile = notNull(thisPartyProfile, "Application profile");
        this.timestamp = notNull(timestamp, "Timestamp");
        this.receiptId = toBase64String(notNull(receiptId, "Receipt id"));
        this.extraData = extraData;
    }

    /**
     * Get the profile associated with the user.
     *
     * @return profile containing attributes for the user
     */
    public HumanProfile getUserProfile() {
        return otherPartyProfile;
    }

    /**
     * Get the profile associated with the application.
     *
     * @return profile containing attributes for the application
     */
    public ApplicationProfile getApplicationProfile() {
        return thisPartyProfile;
    }

    /**
     * Return the rememberMeId, which is a unique, stable identifier for a user in the context of an application.
     * You can use it to identify returning users. This value will be different for the same user in different applications.
     *
     * @return rememberMeId
     */
    public String getRememberMeId() {
        return rememberMeId;
    }

    /**
     * Return the parentRememberMeId, which is a unique, stable identifier for a user in the context of an organisation.
     * You can use it to identify returning users.  This value is consistent for a given user across different apps
     * belonging to a single organisation.
     *
     * @return parentRememberMeId
     */
    public String getParentRememberMeId() {
        return parentRememberMeId;
    }

    /**
     * Time and date of the sharing activity
     *
     * @return time and date of the activity
     */
    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    /**
     * ReceiptId identifying a completed sharing activity.
     *
     * @return receiptId
     */
    public String getReceiptId() {
        return receiptId;
    }

    /**
     * Return the extra data associated with the receipt.
     *
     * @return {@link ExtraData} associated with the receipt
     */
    public ExtraData getExtraData() {
        return extraData;
    }

}
