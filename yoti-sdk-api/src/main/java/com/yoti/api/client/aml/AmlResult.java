package com.yoti.api.client.aml;

/**
 * The results of the aml check.
 *
 */
public interface AmlResult {

    boolean isOnFraudList();

    boolean isOnPepList();

    boolean isOnWatchList();

}
