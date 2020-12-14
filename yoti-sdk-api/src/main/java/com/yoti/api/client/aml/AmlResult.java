package com.yoti.api.client.aml;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The results of the aml check.
 */
public class AmlResult {

    @JsonProperty("on_fraud_list")
    private boolean onFraudList;

    @JsonProperty("on_pep_list")
    private boolean onPepList;

    @JsonProperty("on_watch_list")
    private boolean onWatchList;

    public boolean isOnFraudList() {
        return onFraudList;
    }

    public void setOnFraudList(boolean onFraudList) {
        this.onFraudList = onFraudList;
    }

    public boolean isOnPepList() {
        return onPepList;
    }

    public void setOnPepList(boolean onPepList) {
        this.onPepList = onPepList;
    }

    public boolean isOnWatchList() {
        return onWatchList;
    }

    public void setOnWatchList(boolean onWatchList) {
        this.onWatchList = onWatchList;
    }

}
