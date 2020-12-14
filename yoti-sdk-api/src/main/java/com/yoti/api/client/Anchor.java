package com.yoti.api.client;

import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Anchors represent where, when and how, a profile {@link Attribute} value was acquired or verified. For example, Attributes sourced from a backing
 * document such as a Passport will have a signed, timestamped anchor identifying that source
 */
public class Anchor {

    private final String type;
    private final String subType;
    private final String value;
    private final List<X509Certificate> originCertificates;
    private final SignedTimestamp signedTimestamp;

    public Anchor(String type,
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

    /**
     * The type of Anchor - most likely "SOURCE" or "VERIFIER" but it's possible that new Anchor types will be added in future
     *
     * @return String, naming the type of the anchor
     */
    public String getType() {
        return type;
    }

    /**
     * Identifies the provider that either sourced or verified the attribute value.
     * The range of possible values is not limited.
     * For example, for a passport, this would either be "NFC" or "OCR"
     *
     * @return String, further detailing how the attribute value was obtained
     */
    public String getSubType() {
        return subType;
    }

    /**
     * <pre>
     * For SOURCE Anchors, describes how the attribute value was obtained, among the possible options are: ['USER_PROVIDED', 'PASSPORT', 'DRIVING_LICENSE', 'PASSCARD', 'NATIONAL_ID']
     *
     * For VERIFIER Anchors, describes the verification method used for the attribute, among the possible options are: ['YOTI_ADMIN', 'YOTI_IDENTITY', 'YOTI_OTP', 'PASSPORT_NFC_SIGNATURE', 'ISSUING_AUTHORITY', 'ISSUING_AUTHORITY_PKI']
     * </pre>
     *
     * @return String, naming the entity that anchored the attribute
     */
    public String getValue() {
        return value;
    }

    /**
     * Certificate chain generated when this Anchor was created (attribute value was sourced or verified).  Securely encodes the Anchor type and value.
     *
     * @return Certificate chain encoding the anchor data
     */
    public List<X509Certificate> getOriginCertificates() {
        return originCertificates;
    }

    /**
     * Timestamp applied at the time of Anchor creation.
     *
     * @return {@link SignedTimestamp} representing the time of anchor creation
     */
    public SignedTimestamp getSignedTimestamp() {
        return signedTimestamp;
    }

}
