package com.yoti.api.client.docs.session.create.task;

/**
 * The configuration applied when creating each TextExtractionTask
 */
public interface RequestedTextExtractionTaskConfig extends RequestedTaskConfig {

    /**
     * Describes the manual fallback behaviour applied to each Task
     *
     * @return the manual check value
     */
    String getManualCheck();

    /**
     * Describes how to use chip data from an ID document if
     * it is available
     *
     * @return the chip data usage
     */
    String getChipData();

}
