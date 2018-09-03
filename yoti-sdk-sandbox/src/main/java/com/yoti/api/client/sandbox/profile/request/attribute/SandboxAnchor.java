package com.yoti.api.client.sandbox.profile.request.attribute;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.yoti.api.client.Date;
import com.yoti.api.client.DateTime;
import com.yoti.api.client.Time;
import com.yoti.api.client.spi.remote.util.AnchorType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SandboxAnchor {

    private final String type;
    private final String value;
    private final String subType;
    private final long timestamp;

    private SandboxAnchor(String type, String value, String subType, long timestamp) {
        this.type = type;
        this.value = value;
        this.subType = subType;
        this.timestamp = timestamp;
    }

    public static SandboxAnchor.SandboxAnchorBuilder builder() {
        return new SandboxAnchor.SandboxAnchorBuilder();
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("sub_type")
    public String getSubType() {
        return subType;
    }

    @JsonProperty("timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public static class SandboxAnchorBuilder {

        private String type;
        private String value;
        private String subType;
        private long timestamp;

        private SandboxAnchorBuilder() {
        }

        public SandboxAnchorBuilder withType(AnchorType anchorType) {
            this.type = anchorType.name();
            return this;
        }

        public SandboxAnchorBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public SandboxAnchorBuilder withValue(String value) {
            this.value = value;
            return this;
        }

        public SandboxAnchorBuilder withSubType(String subType) {
            this.subType = subType;
            return this;
        }

        public SandboxAnchorBuilder withTimestamp(DateTime dateTime) {
            Date date = dateTime.getDate();
            Time time = dateTime.getTime();
            Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.set(date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinute(), time.getSecond());
            long timeInMillis = calendar.getTimeInMillis();
            this.timestamp = (timeInMillis * 1000) + time.getMicrosecond();
            return this;
        }

        public SandboxAnchorBuilder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public SandboxAnchor build() {
            return new SandboxAnchor(type, value, subType, timestamp);
        }

    }

}
