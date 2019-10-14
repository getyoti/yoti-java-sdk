package com.yoti.api.client.spi.remote;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yoti.api.client.ExtraData;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.spi.remote.proto.DataEntryProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataEntryConverter {

    private static final Logger LOG = LoggerFactory.getLogger(DataEntryConverter.class);
    private ThirdPartyAttributeConverter thirdPartyAttributeConverter;

    public static DataEntryConverter newInstance() {
        return new DataEntryConverter(
                ThirdPartyAttributeConverter.newInstance()
        );
    }

    DataEntryConverter(ThirdPartyAttributeConverter thirdPartyAttributeConverter) {
        this.thirdPartyAttributeConverter = thirdPartyAttributeConverter;
    }

    public Object convertDataEntry(DataEntryProto.DataEntry dataEntry) throws ExtraDataException {
        Object entry;
        try {
            entry = convertValue(dataEntry.getType(), dataEntry.getValue());
        } catch (InvalidProtocolBufferException e) {
            throw new ExtraDataException("Failed to parse data entry", e);
        } catch (ExtraDataException e) {
            throw e;
        }

        if (entry == null) {
            throw new ExtraDataException("Unsupported/invalid data entry");
        }

        return entry;
    }

    private Object convertValue(DataEntryProto.DataEntry.Type dataEntryType, ByteString value) throws
            InvalidProtocolBufferException, ExtraDataException {
        switch (dataEntryType) {
            case THIRD_PARTY_ATTRIBUTE:
                return thirdPartyAttributeConverter.parseThirdPartyAttribute(value.toByteArray());
            default:
                LOG.warn("Unsupported data entry, skipping...");
                return null;
        }
    }

}
