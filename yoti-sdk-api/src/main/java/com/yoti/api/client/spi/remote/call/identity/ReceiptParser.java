package com.yoti.api.client.spi.remote.call.identity;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

import com.yoti.api.client.ApplicationProfile;
import com.yoti.api.client.Attribute;
import com.yoti.api.client.ExtraData;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.HumanProfile;
import com.yoti.api.client.ProfileException;
import com.yoti.api.client.spi.remote.AttributeListReader;
import com.yoti.api.client.spi.remote.ExtraDataReader;
import com.yoti.crypto.CipherType;
import com.yoti.crypto.Crypto;
import com.yoti.validation.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiptParser {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiptParser.class);

    private final AttributeListReader attributeListReader;
    private final ExtraDataReader extraDataReader;

    private ReceiptParser(AttributeListReader attributeListReader, ExtraDataReader extraDataReader) {
        this.attributeListReader = Validation.notNull(attributeListReader, "profileReader");
        this.extraDataReader = Validation.notNull(extraDataReader, "extraDataReader");
    }

    public static ReceiptParser newInstance() {
        return new ReceiptParser(
                AttributeListReader.newInstance(),
                ExtraDataReader.newInstance()
        );
    }

    public Receipt create(WrappedReceipt wrappedReceipt, ReceiptItemKey wrappedItemKey, PrivateKey privateKey)
            throws DigitalIdentityException {
        Key receiptKey = decryptReceiptKey(wrappedReceipt.getWrappedKey(), wrappedItemKey, privateKey);

        Receipt.Builder receipt = Receipt.forReceipt(wrappedReceipt.getId())
                .sessionId(wrappedReceipt.getSessionId())
                .timestamp(wrappedReceipt.getTimestamp())
                .applicationContent(
                        new ApplicationProfile(parseProfileAttr(wrappedReceipt.getProfile(), receiptKey)),
                        wrappedReceipt.getExtraData().map(data -> parseExtraData(data, receiptKey)).orElse(null)
                );

        receipt.userContent(
                wrappedReceipt.getOtherPartyProfile()
                        .map(profile -> parseProfileAttr(profile, receiptKey))
                        .map(HumanProfile::new)
                        .orElse(null),
                wrappedReceipt.getOtherPartyExtraData()
                        .map(data -> parseExtraData(data, receiptKey))
                        .orElse(null)
        );

        wrappedReceipt.getRememberMeId().map(id -> receipt.rememberMeId(parseRememberMeId(id)));
        wrappedReceipt.getParentRememberMeId().map(id -> receipt.parentRememberMeId(parseRememberMeId(id)));

        return receipt.build();
    }

    public Receipt create(WrappedReceipt failureReceipt) {
        return Receipt.forReceipt(failureReceipt.getId())
                .sessionId(failureReceipt.getSessionId())
                .timestamp(failureReceipt.getTimestamp())
                .error(failureReceipt.getError())
                .build();
    }

    private static Key decryptReceiptKey(byte[] wrappedKey, ReceiptItemKey wrappedItemKey, PrivateKey privateKey) {
        byte[] itemKey = Crypto.decrypt(wrappedItemKey.getValue(), privateKey, CipherType.RSA_NONE_PKCS1);
        byte[] receiptKey = Crypto.decrypt(wrappedKey, itemKey, wrappedItemKey.getIv(), CipherType.AES_GCM);
        return new SecretKeySpec(Objects.requireNonNull(receiptKey), CipherType.AES_CBC.value());
    }

    private List<Attribute<?>> parseProfileAttr(byte[] profile, Key key) {
        try {
            return attributeListReader.read(profile, key);
        } catch (ProfileException ex) {
            throw new DigitalIdentityException(ex);
        }
    }

    private ExtraData parseExtraData(byte[] extraData, Key key) {
        try {
            return extraDataReader.read(extraData, key);
        } catch (ExtraDataException ex) {
            LOG.error("Failed to parse extra data from receipt");
            return new ExtraData();
        } catch (ProfileException ex) {
            throw new DigitalIdentityException(ex);
        }
    }

    private static String parseRememberMeId(byte[] id) {
        return Optional.ofNullable(id)
                .map(v -> new String(Base64.getEncoder().encode(v), StandardCharsets.UTF_8))
                .orElse(null);
    }

}
