package com.yoti.api.client.spi.remote.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.yoti.api.client.spi.remote.proto.AttrProto.Anchor;

public class AnchorCertificateUtils {
    private static final Logger LOG = LoggerFactory.getLogger(AnchorCertificateUtils.class);

   
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

        } catch (Exception e) {
            LOG.warn("Could not extract anchor type from certificate.", e);
        }

        return new AnchorVerifierSourceData(types, anchorType);
    }

    
    private static List<String> getListOfStringFromExtension(X509Certificate certificate, String extensionValue) throws IOException {
        List<String> extensionsStrings = new ArrayList<String>();

        byte[] extension = certificate.getExtensionValue(extensionValue);
        if (extension != null) {
            // Read the First object
            ASN1InputStream asn1InputStream = new ASN1InputStream(extension);
            ASN1Primitive derObject = asn1InputStream.readObject();

            if (derObject != null && derObject instanceof DEROctetString) {
                DEROctetString derOctetString = (DEROctetString) derObject;

                // Read the sub object which is expected to be a sequence
                ASN1InputStream derAsn1stream = new ASN1InputStream(derOctetString.getOctets());
                DLSequence dlSequence = (DLSequence) derAsn1stream.readObject();

                // Enumerate all the objects in the sequence, we expect only one !
                Enumeration<?> secEnum = dlSequence.getObjects();
                while (secEnum.hasMoreElements()) {

                    // This object is OctetString we are looking for
                    ASN1Primitive seqObj = (ASN1Primitive) secEnum.nextElement();
                    ASN1OctetString string = DEROctetString.getInstance((ASN1TaggedObject) seqObj, false);

                    // Convert to a java String
                    extensionsStrings.add(new String(string.getOctets()));
                }
                derAsn1stream.close();
                LOG.debug("Anchor certificate types : '{}' for extension: {}", extensionsStrings.toString(), extensionValue);
            }
            
            asn1InputStream.close();
        }

        return extensionsStrings;
    }

    
    public static byte[] combineBinaryCertChain(List<ByteString> certChain) {
        if (certChain == null || certChain.size() == 0) {
            return new byte[]{};
        }

        if (certChain.size() <= 1) {
            return certChain.get(0).toByteArray();
        }

        ByteArrayOutputStream combinedBytes = new ByteArrayOutputStream();
        try {
            for (ByteString cert : certChain) {
                combinedBytes.write(cert.toByteArray());
            }
        } catch (IOException e) {
            LOG.warn("Error combining byte data for cert chain", e);
        }

        return combinedBytes.toByteArray();
    }

    public static class AnchorVerifierSourceData {
        private Set<String> entries;
        private AnchorType type;

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
