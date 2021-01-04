package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.RFC3339_PATTERN;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SYMMETRIC_CIPHER;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.PrivateKey;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.ApplicationProfile;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.ExtraData;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.util.DecryptionHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ActivityDetailsFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityDetailsFactory.class);

    private final AttributeListReader attributeListReader;
    private final ExtraDataReader extraDataReader;

    private ActivityDetailsFactory(AttributeListReader attributeListReader, ExtraDataReader extraDataReader) {
        this.attributeListReader = notNull(attributeListReader, "profileReader");
        this.extraDataReader = notNull(extraDataReader, "extraDataReader");
    }

    static ActivityDetailsFactory newInstance() {
        return new ActivityDetailsFactory(
                AttributeListReader.newInstance(),
                ExtraDataReader.newInstance()
        );
    }

    ActivityDetails create(Receipt receipt, PrivateKey privateKey) throws ProfileException {
        byte[] decryptedKey = DecryptionHelper.decryptAsymmetric(receipt.getWrappedReceiptKey(), privateKey);
        Key secretKey = new SecretKeySpec(decryptedKey, SYMMETRIC_CIPHER);

        List<Attribute<?>> userProfileAttr = attributeListReader.read(receipt.getOtherPartyProfile(), secretKey);
        List<Attribute<?>> applicationProfileAttr = attributeListReader.read(receipt.getProfile(), secretKey);

        HumanProfile userProfile = new HumanProfile(userProfileAttr);
        ApplicationProfile applicationProfile = new ApplicationProfile(applicationProfileAttr);

        ExtraData extraData = parseExtraData(receipt.getExtraData(), secretKey);

        String rememberMeId = parseRememberMeId(receipt.getRememberMeId());
        String parentRememberMeId = parseRememberMeId(receipt.getParentRememberMeId());
        Date timestamp = parseTimestamp(receipt.getTimestamp());

        return new ActivityDetails(rememberMeId, parentRememberMeId, userProfile, applicationProfile, extraData, timestamp, receipt.getReceiptId());
    }

    private ExtraData parseExtraData(byte[] extraDataBytes, Key secretKey) throws ProfileException {
        ExtraData extraData;
        try {
            extraData = extraDataReader.read(extraDataBytes, secretKey);
        } catch (ExtraDataException e) {
            LOG.error("Failed to parse extra data from receipt");
            extraData = new ExtraData();
        }

        return extraData;
    }

    private String parseRememberMeId(byte[] rmi) throws ProfileException {
        try {
            return (rmi == null) ? null : new String(Base64.getEncoder().encode(rmi), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new ProfileException("Cannot parse user ID", e);
        }
    }

    private Date parseTimestamp(String timestamp) throws ProfileException {
        try {
            DateFormat dateFormat = new SimpleDateFormat(RFC3339_PATTERN);
            return dateFormat.parse(timestamp);
        } catch (ParseException e) {
            throw new ProfileException("Cannot parse timestamp", e);
        }
    }

}
