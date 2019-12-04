package com.yoti.api.client;

import java.util.Date;

/**
 * Details of an activity between a user and the application.
 *
 */
public interface ActivityDetails {

    /**
     * Get the profile associated with the user.
     *
     * @return profile containing attributes for the user
     */
    HumanProfile getUserProfile();

    /**
     * Get the profile associated with the application.
     *
     * @return profile containing attributes for the application
     */
    ApplicationProfile getApplicationProfile();

    /**
     * Deprecated.  Please use getRememberMeId() instead.
     *
     * @return userId (now known as rememberMeId)
     */
    @Deprecated
    String getUserId();

    /**
     * Return the rememberMeId, which is a unique, stable identifier for a user in the context of an application.
     * You can use it to identify returning users. This value will be different for the same user in different applications.
     *
     * @return rememberMeId
     */
    String getRememberMeId();

    /**
     * Return the parentRememberMeId, which is a unique, stable identifier for a user in the context of an organisation.
     * You can use it to identify returning users.  This value is consistent for a given user across different apps
     * belonging to a single organisation.
     *
     * @return parentRememberMeId
     */
    String getParentRememberMeId();

    /**
     * Time and date of the sharing activity
     *
     * @return time and date of the activity
     */
    Date getTimestamp();

    /**
     * ReceiptId identifying a completed sharing activity.
     *
     * @return receiptId
     */
    String getReceiptId();
    
    /**
     * @deprecated From v2.1 onwards you should use getUserProfile().getSelfie()
     * JPEG selfie in Base64 string.
     *
     * @return JPEG selfie image in Base64 string format
     */
    @Deprecated
    String getBase64Selfie();

    /**
     * Return the extra data associated with the receipt.
     */
    ExtraData getExtraData();

}
