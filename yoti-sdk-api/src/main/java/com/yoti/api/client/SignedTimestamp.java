package com.yoti.api.client;

import com.yoti.api.client.spi.remote.util.Validation;

/**
 *  Represents a point in time, accurate to the nearest microsecond.
 *  SignedTimestamps are cryptographically protected at the time of their creation.
 *
 */
public class SignedTimestamp {

    private final int version;
    private final DateTime timestamp;

    public SignedTimestamp(int version, DateTime timestamp) {
        Validation.notNull(timestamp, "timestamp");
        this.version = version;
        this.timestamp = timestamp;
    }

    /**
     * Indicates both the version of the protobuf message in use as well as the specific hash algorithms
     *
     * @return The Yoti SignedTimestamp schema this SignedTimestamp conforms to
     */
    public int getVersion() {
        return version;
    }

    /**
     * A point in time, to the nearest microsecond
     *
     * @return a point in time, to the nearest microsecond
     */
    public DateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("SignedTimestamp{version=%s, timestamp=%s}", version, timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SignedTimestamp that = (SignedTimestamp) o;

        if (version != that.getVersion()) {
            return false;
        }
        return timestamp.equals(that.getTimestamp());
    }

    @Override
    public int hashCode() {
        int result = version;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

}
