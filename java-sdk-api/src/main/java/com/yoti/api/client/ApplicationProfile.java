package com.yoti.api.client;

/**
 * Profile of an application with convenience methods to access well-known attributes.
 *
 *
 */
public interface ApplicationProfile extends Profile {
    /**
     * The name of the application.
     * @return The name of the application.
     */
    String getApplicationName();

    /**
     * The URI verified by Portal where the application is available at
     * @return The URI verified by Portal where the application is available at
     */
    String getApplicationUrl();

    /**
     * The logo of the application that will be displayed to those users that perform a sharing with it.
     * @return The logo of the application that will be displayed to those users that perform a sharing with it.
     */
    Image getApplicationLogo();

    /**
     * The background colour that will be displayed on each receipt the user gets as a result of a sharing with the
     * application.
     * 
     * @return The background colour that will be displayed on each receipt the user gets as a result of a sharing with the
     * application.
     */
    String getApplicationReceiptBgColor();
}
