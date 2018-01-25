package com.yoti.api.client.spi.remote.call.aml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yoti.api.client.aml.AmlResult;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleAmlResult implements AmlResult {

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

    @Override
    public String toString() {
        return "SimpleAmlResult{" +
                "onFraudList=" + onFraudList +
                ", onPepList=" + onPepList +
                ", onWatchList=" + onWatchList +
                '}';
    }
}
