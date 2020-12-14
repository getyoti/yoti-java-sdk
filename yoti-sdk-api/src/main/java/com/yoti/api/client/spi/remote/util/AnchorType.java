package com.yoti.api.client.spi.remote.util;

public enum AnchorType {

    SOURCE("1.3.6.1.4.1.47127.1.1.1"),
    VERIFIER("1.3.6.1.4.1.47127.1.1.2"),
    UNKNOWN("");

    public final String extensionOid;

    AnchorType(String extensionOid) {
        this.extensionOid = extensionOid;
    }

}
