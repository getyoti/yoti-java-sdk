package com.yoti.api.client;

/**
 *  Represents a point in time, accurate to the nearest microsecond.
 *  SignedTimestamps are cryptographically protected at the time of their creation.
 *
 */
public interface SignedTimestamp {

    /**
     * Indicates both the version of the protobuf message in use as well as the specific hash algorithms
     *
     * @return The Yoti SignedTimestamp schema this SignedTimestamp conforms to
     */
    int getVersion();

    /**
     * A point in time, to the nearest microsecond
     *
     * @return a point in time, to the nearest microsecond
     */
    DateTime getTimestamp();

}
