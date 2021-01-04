package com.yoti.api.client.spi.remote;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.ProfileException;

class AttributeListReader {

    private final EncryptedDataReader encryptedDataReader;
    private final AttributeListConverter attributeListConverter;

    private AttributeListReader(EncryptedDataReader encryptedDataReader,
                          AttributeListConverter attributeListConverter) {
        this.encryptedDataReader = encryptedDataReader;
        this.attributeListConverter = attributeListConverter;
    }

    static AttributeListReader newInstance() {
        return new AttributeListReader(
                EncryptedDataReader.newInstance(),
                AttributeListConverter.newInstance()
        );
    }

    List<Attribute<?>> read(byte[] encryptedProfileBytes, Key secretKey) throws ProfileException {
        List<Attribute<?>> attributeList = new ArrayList<>();
        if (encryptedProfileBytes != null && encryptedProfileBytes.length > 0) {
            byte[] profileData = encryptedDataReader.decryptBytes(encryptedProfileBytes, secretKey);
            attributeList = attributeListConverter.parseAttributeList(profileData);
        }
        return attributeList;
    }

}
