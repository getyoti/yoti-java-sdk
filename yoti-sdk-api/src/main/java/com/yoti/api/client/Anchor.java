package com.yoti.api.client;

import java.security.cert.X509Certificate;
import java.util.List;

public interface Anchor {

    String getType();

    String getSubType();

    String getValue();

    List<X509Certificate> getOriginCertificates();

    SignedTimestamp getSignedTimestamp();

}
