package com.yoti.api.client.docs.session.create.task;

/**
 * The configuration applied when creating each SupplementaryTextExtractionTask
 */
public interface RequestedSupplementaryDocTextExtractionTaskConfig extends RequestedTaskConfig {

    /**
     * Describes the manual fallback behaviour applied to each Task
     *
     * @return the manual check value
     */
    String getManualCheck();

}
