package com.yoti.api.client;

/**
 * <p>
 * Entry point to interact with the Yoti Connect API.
 * </p>
 * <p>
 * It can be safely cached and shared even by multiple threads.
 * </p>
 * a
 */
public interface YotiClient {

    /**
     * Get the activity details for a token. Amongst others contains the user profile with the user's attributes you
     * have selected in your application configuration on Yoti Portal.
     *
     * @param encryptedYotiToken
     *            encrypted Yoti token (can be only decrypted with your application's private key)
     * @return an {@link ActivityDetails} instance holding the user's attributes
     *
     * @throws ProfileException
     *             aggregate exception signalling issues during the call
     */
    ActivityDetails getActivityDetails(String encryptedYotiToken) throws ProfileException;
}
