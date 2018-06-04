package com.yoti.api.client.qrcode.policy;

import java.util.List;

/**
 * Attribute
 */
public interface Attribute {

    /**
     * getName
     */
    String getName();

    /**
     * getAnchors
     */
    List<String> getAnchors();

    /**
     * getDerivation
     */
    String getDerivation();

    /**
     * isOptional
     */
    boolean isOptional();

}
