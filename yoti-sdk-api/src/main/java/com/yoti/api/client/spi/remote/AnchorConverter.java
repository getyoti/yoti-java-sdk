package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.yoti.api.client.Anchor;
import com.yoti.api.client.DateTime;
import com.yoti.api.client.SignedTimestamp;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.proto.SignedTimestampProto;
import com.yoti.api.client.spi.remote.util.AnchorType;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AnchorConverter {

    private static final Logger LOG = LoggerFactory.getLogger(AnchorConverter.class);

    Anchor convert(AttrProto.Anchor anchorProto) throws CertificateException, IOException {
        List<X509Certificate> certificates = convertCertificates(anchorProto);
        AnchorTypeAndValue anchorTypeAndValue = determineAnchorType(certificates);
        String anchorType = anchorTypeAndValue.anchorType.name();
        String value = anchorTypeAndValue.value;
        SignedTimestamp signedTimestamp = convertSignedTimestamp(anchorProto.getSignedTimeStamp());

        return new Anchor(anchorType, anchorProto.getSubType(), value, certificates, signedTimestamp);
    }

    private List<X509Certificate> convertCertificates(AttrProto.Anchor anchorProto) throws CertificateException {
        List<X509Certificate> certificates = new ArrayList<>();
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        ByteArrayInputStream byteArrayInputStream;
        for (ByteString byteString : anchorProto.getOriginServerCertsList()) {
            byteArrayInputStream = new ByteArrayInputStream(byteString.toByteArray());
            certificates.add((X509Certificate) factory.generateCertificate(byteArrayInputStream));
        }
        return certificates;
    }

    private AnchorTypeAndValue determineAnchorType(List<X509Certificate> certificates) throws IOException {
        for (X509Certificate certificate : certificates) {
            for (AnchorType anchorType : AnchorType.values()) {
                String type = findExtension(certificate, anchorType);
                if (type != null) {
                    return new AnchorTypeAndValue(anchorType, type);
                }
            }
        }
        return new AnchorTypeAndValue(AnchorType.UNKNOWN, "");
    }

    private String findExtension(X509Certificate certificate, AnchorType anchorType) throws IOException {
        byte[] extension = certificate.getExtensionValue(anchorType.extensionOid);
        if (extension != null) {
            // Read the First object
            ASN1Primitive derObject = ASN1Primitive.fromByteArray(extension);
            if (derObject instanceof DEROctetString) {
                DEROctetString derOctetString = (DEROctetString) derObject;
                // Read the sub object which is expected to be a sequence
                ASN1Primitive asn1Primitive1 = ASN1Primitive.fromByteArray(derOctetString.getOctets());
                DLSequence dlSequence = (DLSequence) asn1Primitive1;
                // Enumerate all the objects in the sequence (though we expect only one)
                Enumeration<?> seqEnum = dlSequence.getObjects();
                if (seqEnum.hasMoreElements()) {
                    // This object is OctetString we are looking for
                    ASN1TaggedObject seqObj = (ASN1TaggedObject) seqEnum.nextElement();
                    ASN1OctetString string = DEROctetString.getInstance(seqObj, false);
                    // Convert to a java String
                    String type = new String(string.getOctets(), DEFAULT_CHARSET);
                    LOG.debug("Anchor certificate type: '{}' for extension: {}", type, anchorType.extensionOid);
                    return type;
                }
            }
        }
        return null;
    }

    private SignedTimestamp convertSignedTimestamp(ByteString timestampByteString) throws InvalidProtocolBufferException {
        SignedTimestampProto.SignedTimestamp timestampProto = SignedTimestampProto.SignedTimestamp.parseFrom(timestampByteString);
        return new SignedTimestamp(timestampProto.getVersion(), DateTime.from(timestampProto.getTimestamp()));
    }

    private static class AnchorTypeAndValue {

        private final AnchorType anchorType;
        private final String value;

        private AnchorTypeAndValue(AnchorType anchorType, String value) {
            this.anchorType = anchorType;
            this.value = value;
        }
    }

}
