package com.yoti.api.client.spi.remote;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Profile;
import com.yoti.api.client.ProfileException;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

class ProfileReader {

    private final EncryptedDataReader encryptedDataReader;
    private final AttributeListConverter attributeListConverter;

    private ProfileReader(EncryptedDataReader encryptedDataReader,
                          AttributeListConverter attributeListConverter) {
        this.encryptedDataReader = encryptedDataReader;
        this.attributeListConverter = attributeListConverter;
    }

    static ProfileReader newInstance() {
        return new ProfileReader(
                new EncryptedDataReader(),
                AttributeListConverter.newInstance()
        );
    }

    Profile read(byte[] encryptedProfileBytes, Key secretKey) throws ProfileException {
        List<Attribute<?>> attributeList = new ArrayList<>();
        if (encryptedProfileBytes != null && encryptedProfileBytes.length > 0) {
            byte[] profileData = encryptedDataReader.decryptBytes(encryptedProfileBytes, secretKey);
            attributeList = attributeListConverter.parseAttributeList(profileData);
        }
        return new SimpleProfile(attributeList);
    }

}
