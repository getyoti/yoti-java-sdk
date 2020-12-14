package com.yoti.api.client.spi.remote;

import com.yoti.api.client.DateTime;
import com.yoti.api.client.SignedTimestamp;
import com.yoti.api.client.spi.remote.util.Validation;

public class SignedTimestampValue implements SignedTimestamp {

    private final int version;
    private final DateTime timestamp;

    public SignedTimestampValue(int version, DateTime timestamp) {
        Validation.notNull(timestamp, "timestamp");
        this.version = version;
        this.timestamp = timestamp;
    }

    public int getVersion() {
        return version;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("SignedTimestampValue{version=%s, timestamp=%s}", version, timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SignedTimestampValue that = (SignedTimestampValue) o;

        if (version != that.version) {
            return false;
        }
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = version;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

}
