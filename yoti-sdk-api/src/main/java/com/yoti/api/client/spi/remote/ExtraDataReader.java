package com.yoti.api.client.spi.remote;

import java.security.Key;

import com.yoti.api.client.ExtraData;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.ProfileException;

public class ExtraDataReader {

    private final EncryptedDataReader encryptedDataReader;
    private final ExtraDataConverter extraDataConverter;

    private ExtraDataReader(
            EncryptedDataReader encryptedDataReader,
            ExtraDataConverter extraDataConverter) {
        this.encryptedDataReader = encryptedDataReader;
        this.extraDataConverter = extraDataConverter;
    }

    public static ExtraDataReader newInstance() {
        return new ExtraDataReader(
                EncryptedDataReader.newInstance(),
                ExtraDataConverter.newInstance()
        );
    }

    public ExtraData read(byte[] encryptedBytes, Key secretKey) throws ProfileException, ExtraDataException {
        ExtraData extraData;
        if (encryptedBytes != null && encryptedBytes.length > 0) {
            byte[] extraDataBytes = encryptedDataReader.decryptBytes(encryptedBytes, secretKey);
            extraData = extraDataConverter.read(extraDataBytes);
        } else {
            extraData = new ExtraData();
        }

        return extraData;
    }

}
