package com.yoti.api.client.spi.remote.util;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yoti.api.client.spi.remote.proto.AttrProto.Anchor;

import com.google.protobuf.ByteString;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnchorCertificateParser {

    private static final Logger LOG = LoggerFactory.getLogger(AnchorCertificateParser.class);

    public static AnchorVerifierSourceData getTypesFromAnchor(Anchor anchor) {
        Set<String> types = new HashSet<String>(anchor.getOriginServerCertsCount());
        AnchorType anchorType = AnchorType.UNKNOWN;

        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");

            for (ByteString bs : anchor.getOriginServerCertsList()) {
                X509Certificate certificate = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(bs.toByteArray()));

                List<String> extensions = new ArrayList<String>();
                for (AnchorType type : AnchorType.values()) {
                    extensions = getListOfStringFromExtension(certificate, type.extensionOid);
                    if (extensions.size() > 0) {
                        anchorType = type;
                        break;
                    }
                }
                types.addAll(extensions);
            }
        } catch (IOException e) {
            LOG.warn("Could not extract anchor type from certificate.", e);
        } catch (CertificateException e) {
            LOG.warn("Could not extract anchor type from certificate.", e);
        }

        return new AnchorVerifierSourceData(types, anchorType);
    }

    private static List<String> getListOfStringFromExtension(X509Certificate certificate, String extensionValue) throws IOException {
        List<String> extensionsStrings = new ArrayList<String>();

        byte[] extension = certificate.getExtensionValue(extensionValue);
        if (extension != null) {
            // Read the First object
            ASN1Primitive derObject = ASN1Primitive.fromByteArray(extension);

            if (derObject != null && derObject instanceof DEROctetString) {
                DEROctetString derOctetString = (DEROctetString) derObject;

                // Read the sub object which is expected to be a sequence
                ASN1Primitive asn1Primitive1 = ASN1Primitive.fromByteArray(derOctetString.getOctets());
                DLSequence dlSequence = (DLSequence) asn1Primitive1;

                // Enumerate all the objects in the sequence, we expect only one !
                Enumeration<?> seqEnum = dlSequence.getObjects();
                while (seqEnum.hasMoreElements()) {

                    // This object is OctetString we are looking for
                    ASN1TaggedObject seqObj = (ASN1TaggedObject) seqEnum.nextElement();
                    ASN1OctetString string = DEROctetString.getInstance(seqObj, false);

                    // Convert to a java String
                    extensionsStrings.add(new String(string.getOctets(), DEFAULT_CHARSET));
                }

                LOG.debug("Anchor certificate types : '{}' for extension: {}", extensionsStrings.toString(), extensionValue);
            }
        }
        return extensionsStrings;
    }

    public static class AnchorVerifierSourceData {
        private final Set<String> entries;
        private final AnchorType type;

        public AnchorVerifierSourceData(Set<String> entries, AnchorType anchorType) {
            this.entries = entries;
            this.type = anchorType;
        }

        public Set<String> getEntries() {
            return entries;
        }

        public AnchorType getType() {
            return type;
        }
    }
}
