package com.yoti.api.client.spi.remote.util;

public enum AnchorType {

    SOURCE("SOURCE", "1.3.6.1.4.1.47127.1.1.1"),
    VERIFIER("VERIFIER", "1.3.6.1.4.1.47127.1.1.2"),
    UNKNOWN("UNKNOWN", "");

    public final String stringValue;
    public final String extensionOid;

    AnchorType(String stringValue, String enxtensionOid) {
        this.stringValue = stringValue;
        this.extensionOid = enxtensionOid;
    }
}
