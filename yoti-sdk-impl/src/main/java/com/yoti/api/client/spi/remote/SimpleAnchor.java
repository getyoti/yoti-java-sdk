package com.yoti.api.client.spi.remote;

import java.security.cert.X509Certificate;
import java.util.List;

import com.yoti.api.client.Anchor;
import com.yoti.api.client.SignedTimestamp;

public class SimpleAnchor implements Anchor {

    private final String type;
    private final String subType;
    private final String value;
    private final List<X509Certificate> originCertificates;
    private final SignedTimestamp signedTimestamp;

    public SimpleAnchor(String type,
            String subType,
            String value,
            List<X509Certificate> originCertificates,
            SignedTimestamp signedTimestamp) {
        this.type = type;
        this.subType = subType;
        this.value = value;
        this.originCertificates = originCertificates;
        this.signedTimestamp = signedTimestamp;
    }

    public String getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getValue() {
        return value;
    }

    public List<X509Certificate> getOriginCertificates() {
        return originCertificates;
    }

    public SignedTimestamp getSignedTimestamp() {
        return signedTimestamp;
    }

}
