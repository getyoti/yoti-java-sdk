package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.call.YotiConstants.DEFAULT_CHARSET;
import static com.yoti.api.client.spi.remote.call.YotiConstants.SYMMETRIC_CIPHER;
import static com.yoti.api.client.spi.remote.util.Validation.notNull;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.PrivateKey;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import com.yoti.api.client.ActivityDetails;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.call.Receipt;
import com.yoti.api.client.spi.remote.util.Decryption;

public class ActivityDetailsFactory {

    private static final String RFC3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private final ProfileReader profileReader;

    private ActivityDetailsFactory(ProfileReader profileReader) {
        this.profileReader = notNull(profileReader, "profileReader");
    }

    public static ActivityDetailsFactory newInstance() {
        return new ActivityDetailsFactory(ProfileReader.newInstance());
    }

    public ActivityDetails create(Receipt receipt, PrivateKey privateKey) throws ProfileException {
        byte[] decryptedKey = Decryption.decryptAsymmetric(receipt.getWrappedReceiptKey(), privateKey);
        Key secretKey = new SecretKeySpec(decryptedKey, SYMMETRIC_CIPHER);

        Profile userProfile = profileReader.read(receipt.getOtherPartyProfile(), secretKey);
        Profile applicationProfile = profileReader.read(receipt.getProfile(), secretKey);
        String rememberMeId = parseRememberMeId(receipt.getRememberMeId());
        Date timestamp = parseTimestamp(receipt.getTimestamp());

        return new SimpleActivityDetails(rememberMeId, userProfile, applicationProfile, timestamp, receipt.getReceiptId());
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
