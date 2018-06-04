package com.yoti.api.client.qrcode.extension;

/**
 * Extension
 */
public interface Extension {

    /**
     * getType
     */
    Type getType();

    /**
     * getContent
     */
    String getContent();

    /**
     *
     */
    static enum Type {
        WORLD_PAY_PAYMENT,
        LOCATION_CONSTRAINT,
        TRANSACTIONAL_FLOW
    }

}
