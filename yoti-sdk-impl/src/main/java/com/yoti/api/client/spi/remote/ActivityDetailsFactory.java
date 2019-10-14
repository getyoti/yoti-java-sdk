package com.yoti.api.client.spi.remote;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.ExtraData;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.util.DecryptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.PrivateKey;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.yoti.api.client.spi.remote.call.YotiConstants.*;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

class ActivityDetailsFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityDetailsFactory.class);

    private final ProfileReader profileReader;
    private final ExtraDataConverter extraDataConverter;

    private ActivityDetailsFactory(ProfileReader profileReader, ExtraDataConverter extraDataConverter) {
        this.profileReader = notNull(profileReader, "profileReader");
        this.extraDataConverter = notNull(extraDataConverter, "extraDataConverter");
    }

    static ActivityDetailsFactory newInstance() {
        return new ActivityDetailsFactory(
                ProfileReader.newInstance(),
                ExtraDataConverter.newInstance()
        );
    }

    ActivityDetails create(Receipt receipt, PrivateKey privateKey) throws ProfileException {
        byte[] decryptedKey = DecryptionHelper.decryptAsymmetric(receipt.getWrappedReceiptKey(), privateKey);
        Key secretKey = new SecretKeySpec(decryptedKey, SYMMETRIC_CIPHER);

        Profile userProfile = profileReader.read(receipt.getOtherPartyProfile(), secretKey);
        Profile applicationProfile = profileReader.read(receipt.getProfile(), secretKey);

        ExtraData extraData = parseExtraData(receipt.getExtraData());

        String rememberMeId = parseRememberMeId(receipt.getRememberMeId());
        String parentRememberMeId = parseRememberMeId(receipt.getParentRememberMeId());
        Date timestamp = parseTimestamp(receipt.getTimestamp());

        return new SimpleActivityDetails(rememberMeId, parentRememberMeId, userProfile, applicationProfile, extraData, timestamp, receipt.getReceiptId());
    }

    private ExtraData parseExtraData(byte[] extraDataBytes) {
        ExtraData extraData;
        try {
            extraData = extraDataConverter.read(extraDataBytes);
        } catch (ExtraDataException e) {
            LOG.error("Failed to parse extra data from receipt");
            extraData = new SimpleExtraData();
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
