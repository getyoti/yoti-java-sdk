package com.yoti.api.client;

import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Anchors represent where, when and how, a profile {@link Attribute} value was acquired or verified. For example, Attributes sourced from a backing
 * document such as a Passport will have a signed, timestamped anchor identifying that source
 */
public interface Anchor {

    /**
     * The type of Anchor - most likely "SOURCE" or "VERIFIER" but it's possible that new Anchor types will be added in future
     * @return String, naming the type of the anchor
     */
    String getType();

    /**
     * Identifies the provider that either sourced or verified the attribute value.
     * The range of possible values is not limited.
     * For example, for a passport, this would either be "NFC" or "OCR"
     *
     * @return String, further detailing how the attribute value was obtained
     */
    String getSubType();

    /**
     * <pre>
     * For SOURCE Anchors, describes how the attribute value was obtained, options listed are among possible values: ['USER_PROVIDED', 'PASSPORT', 'DRIVING_LICENSE', 'PASSCARD', 'NATIONAL_ID']
     *
     * For VERIFIER Anchors, describes the verification method used for the attribute, options listed are among possible values: ['YOTI_ADMIN', 'YOTI_IDENTITY', 'YOTI_OTP', 'PASSPORT_NFC_SIGNATURE', 'ISSUING_AUTHORITY', 'ISSUING_AUTHORITY_PKI']
     * </pre>
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
