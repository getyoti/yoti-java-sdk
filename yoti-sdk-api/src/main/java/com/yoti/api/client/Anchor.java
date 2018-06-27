package com.yoti.api.client;

import java.security.cert.X509Certificate;
import java.util.List;

public interface Anchor {

    /**
     * The type of Anchor - most likely either SOURCE or VERIFIER, but it's possible that new Anchor types will be added in future
     *
     * @return String, naming the type of the anchor
     */
    String getType();

    /**
     * For SOURCE Anchors, describes how the attribute value was obtained, for example this might be 'OCR' or 'NFC'
     * For VERIFIER Anchors, describes the verification method used for the attribute
     *
     * @return String, further detailing how the attribute value was obtained
     */
    String getSubType();

    /**
     * Identifies the provider that either sourced or verified the attribute value.
     * The range of possible values is not limited.  For a SOURCE anchor, expect values like PASSPORT, DRIVING_LICENSE...
     *
     * @return String, naming the entity that anchored the attribute
     */
    String getValue();

    /**
     * Certificate chain generated when this Anchor was created (attribute value was sourced or verified).  Securely encodes the Anchor type and value.
     *
     * @return Certificate chain encoding the anchor data
     */
    List<X509Certificate> getOriginCertificates();

    /**
     * Timestamp applied at the time of Anchor creation.
     *
     * @return {@link SignedTimestamp} representing the time of anchor creation
     */
    SignedTimestamp getSignedTimestamp();

}
