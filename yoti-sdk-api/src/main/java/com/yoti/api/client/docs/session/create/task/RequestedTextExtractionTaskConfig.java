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

}
