package com.yoti.api.client.spi.remote;

import com.yoti.api.client.DateTime;
import com.yoti.api.client.SignedTimestamp;

public class SignedTimestampValue implements SignedTimestamp {

    private final int version;
    private final DateTime timestamp;

    public SignedTimestampValue(int version, DateTime timestamp) {
        this.version = version;
        this.timestamp = timestamp;
    }

    public int getVersion() {
        return version;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

}
