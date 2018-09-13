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
     * @return user ID.
     */
    @Deprecated
    String getUserId();

    /**
     * Return the remember-me ID, which is a unique, stable identifier for a user in the context of an application/user
     * interaction. You can be use it to identify returning users. Note that it is different for the same user in
     * different applications.
     *
     * @return user ID.
     */
    String getRememberMeId();


    /**
     * Time and date of the activity.
     *
     * @return time and date of the activity
     */
    Date getTimestamp();

    /**
     * Receipt id identifying a completed activity.
     *
     * @return receipt id
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

}
