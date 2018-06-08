package com.yoti.api.client.qrcode.extension;

/**
 * Type and content of a feature for an application
 *
 */
public interface Extension {

    /**
     * Get the feature's {@link Type}
     *
     * @return the type of the operation
     */
    Type getType();

    /**
     * Get the feature's details
     *
     * @return the payload of the operation
     */
    String getContent();

    /**
     * Supported features
     */
    static enum Type {
        WORLD_PAY_PAYMENT,
        LOCATION_CONSTRAINT,
        TRANSACTIONAL_FLOW
    }

}
