package com.yoti.api.client.spi.remote;

import com.yoti.api.client.ExtraData;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.ProfileException;

import java.security.Key;

public class ExtraDataReader {

    private final EncryptedDataReader encryptedDataReader;
    private final ExtraDataConverter extraDataConverter;

    private ExtraDataReader(
            EncryptedDataReader encryptedDataReader,
            ExtraDataConverter extraDataConverter) {
        this.encryptedDataReader = encryptedDataReader;
        this.extraDataConverter = extraDataConverter;
    }

    static ExtraDataReader newInstance() {
        return new ExtraDataReader(
                new EncryptedDataReader(),
                ExtraDataConverter.newInstance()
        );
    }

    ExtraData read(byte[] encryptedBytes, Key secretKey) throws ProfileException, ExtraDataException {
        ExtraData extraData = null;
        if (encryptedBytes != null && encryptedBytes.length > 0) {
            byte[] extraDataBytes = encryptedDataReader.decryptBytes(encryptedBytes, secretKey);
            extraData = extraDataConverter.read(extraDataBytes);
        } else {
            extraData = new SimpleExtraData();
        }

        return extraData;
    }
}
