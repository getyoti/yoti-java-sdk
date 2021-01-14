package com.yoti.api.client.spi.remote;

import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;

import java.security.Security;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.yoti.api.client.Anchor;
import com.yoti.api.client.Date;
import com.yoti.api.client.SignedTimestamp;
import com.yoti.api.client.Time;
import com.yoti.api.client.spi.remote.proto.AttrProto;
import com.yoti.api.client.spi.remote.util.AnchorType;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Test;

public class AnchorConverterTest {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String PASSPORT_ISSUER = "CN=passport-registration-server";
    private static final String DRIVING_LICENCE_ISSUER = "CN=driving-licence-registration-server";
    private static final String DOCUMENT_REGISTRATION_ISSUER = "CN=document-registration-server";
    private static final String PASSPORT_SOURCE_TYPE = "PASSPORT";
    private static final String DRIVING_LICENCE_SOURCE_TYPE = "DRIVING_LICENCE";
    private static final String YOTI_ADMIN_VERIFIER_TYPE = "YOTI_ADMIN";
    private static final String SHA256_ALGO = "SHA256withRSA";
    private static final String CRITICAL_EXT_ID = "2.5.29.15";

    AnchorConverter testObj = new AnchorConverter();

    @Test
    public void shouldConvertPassportAnchor() throws Exception {
        AttrProto.Anchor anchorProto = AttrProto.Anchor.parseFrom(Base64.getDecoder().decode(TestAnchors.PP_ANCHOR));

        Anchor result = testObj.convert(anchorProto);

        assertEquals(TestAnchors.PP_ANCHOR_TYPE, result.getType());
        assertEquals(TestAnchors.PP_ANCHOR_SUBTYPE, result.getSubType());
        assertEquals(PASSPORT_SOURCE_TYPE, result.getValue());
        verifyCertificates(result.getOriginCertificates(), PASSPORT_ISSUER, AnchorType.SOURCE.extensionOid);
        verifyTimestamp(result.getSignedTimestamp(), TestAnchors.PP_ANCHOR_TIMESTAMP);
    }

    @Test
    public void shouldConvertDrivingLicenseAnchor() throws Exception {
        AttrProto.Anchor anchorProto = AttrProto.Anchor.parseFrom(Base64.getDecoder().decode(TestAnchors.DL_ANCHOR));

        Anchor result = testObj.convert(anchorProto);

        assertEquals(TestAnchors.DL_ANCHOR_TYPE, result.getType());
        assertEquals(TestAnchors.DL_ANCHOR_SUBTYPE, result.getSubType());
        assertEquals(DRIVING_LICENCE_SOURCE_TYPE, result.getValue());
        verifyCertificates(result.getOriginCertificates(), DRIVING_LICENCE_ISSUER, AnchorType.SOURCE.extensionOid);
        verifyTimestamp(result.getSignedTimestamp(), TestAnchors.DL_ANCHOR_TIMESTAMP);
    }

    @Test
    public void shouldConvertYotiAdminAnchor() throws Exception {
        AttrProto.Anchor anchorProto = AttrProto.Anchor.parseFrom(Base64.getDecoder().decode(TestAnchors.YOTI_ADMIN_ANCHOR));

        Anchor result = testObj.convert(anchorProto);

        assertEquals(TestAnchors.YOTI_ADMIN_ANCHOR_TYPE, result.getType());
        assertEquals(TestAnchors.YOTI_ADMIN_ANCHOR_SUBTYPE, result.getSubType());
        assertEquals(YOTI_ADMIN_VERIFIER_TYPE, result.getValue());
        verifyCertificates(result.getOriginCertificates(), DRIVING_LICENCE_ISSUER, AnchorType.VERIFIER.extensionOid);
        verifyTimestamp(result.getSignedTimestamp(), TestAnchors.YOTI_ADMIN_ANCHOR_TIMESTAMP);
    }

    @Test
    public void shouldConvertYotiUnknownAnchor() throws Exception {
        AttrProto.Anchor anchorProto = AttrProto.Anchor.parseFrom(Base64.getDecoder().decode(TestAnchors.YOTI_UNKNOWN_ANCHOR));

        Anchor result = testObj.convert(anchorProto);

        assertEquals(TestAnchors.YOTI_UNKNOWN_ANCHOR_TYPE, result.getType());
        assertEquals(TestAnchors.YOTI_UNKNOWN_ANCHOR_SUBTYPE, result.getSubType());
        assertEquals(TestAnchors.YOTI_UNKNOWN_ANCHOR_VALUE, result.getValue());
        assertEquals(TestAnchors.YOTI_UNKNOWN_ANCHOR_SERIAL, result.getOriginCertificates().get(0).getSerialNumber().toString());
        assertEquals(DOCUMENT_REGISTRATION_ISSUER, result.getOriginCertificates().get(0).getIssuerDN().toString());
        verifyTimestamp(result.getSignedTimestamp(), TestAnchors.YOTI_UNKNOWN_ANCHOR_TIMESTAMP);
    }

    private void verifyCertificates(List<X509Certificate> certificates, String issuer, String nonCriticalExtIds) {
        CertficateMatcher expectedCertificate = new CertficateMatcher(issuer, SHA256_ALGO, asList(CRITICAL_EXT_ID), asList(nonCriticalExtIds));
        assertThat(certificates, hasSize(1));
        assertThat(certificates, hasItem(expectedCertificate));
    }

    private void verifyTimestamp(SignedTimestamp signedTimestamp, String expected) throws Exception {
        int micros = Integer.parseInt(expected.substring(expected.lastIndexOf(".") + 1));
        expected = expected.substring(0, expected.lastIndexOf("."));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(expected));

        assertEquals(1, signedTimestamp.getVersion());

        Date timestampDate = signedTimestamp.getTimestamp().getDate();
        assertEquals("Year", calendar.get(Calendar.YEAR), timestampDate.getYear());
        assertEquals("Month", calendar.get(Calendar.MONTH) + 1, timestampDate.getMonth());
        assertEquals("Day", calendar.get(Calendar.DAY_OF_MONTH), timestampDate.getDay());

        Time timestampTime = signedTimestamp.getTimestamp().getTime();
        assertEquals("Hour", calendar.get(Calendar.HOUR_OF_DAY), timestampTime.getHour());
        assertEquals("Minute", calendar.get(Calendar.MINUTE), timestampTime.getMinute());
        assertEquals("Second", calendar.get(Calendar.SECOND), timestampTime.getSecond());
        assertEquals("micro", micros, timestampTime.getMicrosecond());
    }

    private static class CertficateMatcher extends TypeSafeDiagnosingMatcher<X509Certificate> {

        private static final String TEMPLATE = "[\nIssuerDN:%s\nSubjectDN:%s\nSigAlgName:%s\nCriticalExtensionOIDs:%s\nNonCriticalExtensionOIDs:%s\n]";

        private final String issuer;
        private final String algorithm;
        private final List<String> criticalExtIds;
        private final List<String> nonCriticalExtIds;

        private CertficateMatcher(String issuer, String algorithm, List<String> criticalExtIds, List<String> nonCriticalExtIds) {
            this.issuer = issuer;
            this.algorithm = algorithm;
            this.criticalExtIds = criticalExtIds;
            this.nonCriticalExtIds = nonCriticalExtIds;
        }

        @Override
        protected boolean matchesSafely(X509Certificate x509Cert, Description description) {
            description.appendText(describe(
                    x509Cert.getIssuerDN().getName(),
                    x509Cert.getSubjectDN().getName(),
                    x509Cert.getSigAlgName(),
                    x509Cert.getCriticalExtensionOIDs(),
                    x509Cert.getNonCriticalExtensionOIDs()));
            return issuer.equals(x509Cert.getIssuerDN().getName())
                    && issuer.equals(x509Cert.getSubjectDN().getName())
                    && algorithm.equals(x509Cert.getSigAlgName())
                    && hasExpectedItems(criticalExtIds, x509Cert.getCriticalExtensionOIDs())
                    && hasExpectedItems(nonCriticalExtIds, x509Cert.getNonCriticalExtensionOIDs());
        }

        private boolean hasExpectedItems(List<String> expected, Set<String> actual) {
            return IsCollectionWithSize.hasSize(expected.size()).matches(actual)
                    && IsCollectionContaining.hasItems(expected.toArray()).matches(actual);
        }

        private static String describe(String issuer,
                String subject,
                String algorithm,
                Collection<String> criticalExtIds,
                Collection<String> nonCriticalExtIds) {
            return String.format(TEMPLATE, issuer, subject, algorithm, criticalExtIds, nonCriticalExtIds);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(describe(issuer, issuer, algorithm, criticalExtIds, nonCriticalExtIds));
        }

    }

}

