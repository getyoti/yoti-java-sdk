package com.yoti.api.client;

/**
 * Wraps an 'Age Verify/Condition' attribute to provide behaviour specific to verifying someone's age
 */
public interface AgeVerification {

    /**
     * The type of age check performed, as specified on dashboard.  Currently this might be 'age_over' or 'age_under'
     *
     * @return The type of age check performed
     */
    String getCheckPerformed();

    /**
     * The age that was that checked, as specified on dashboard.
     *
     * @return The age that was that checked
     */
    int getAgeVerified();

    /**
     * Whether or not the profile passed the age check
     *
     * @return The result of the age check.
     */
    boolean getResult();

    /**
     * The wrapped profile attribute.  Use this if you need access to the underlying List of {@link Anchor}s
     *
     * @return The wrapped profile attribute
     */
    Attribute<String> getAttribute();

}
