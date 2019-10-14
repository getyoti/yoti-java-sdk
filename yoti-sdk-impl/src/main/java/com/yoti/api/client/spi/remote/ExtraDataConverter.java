package com.yoti.api.client.spi.remote;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yoti.api.client.ExtraData;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.spi.remote.proto.DataEntryProto;
import com.yoti.api.client.spi.remote.proto.ExtraDataProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExtraDataConverter {

    private static final Logger LOG = LoggerFactory.getLogger(ExtraDataConverter.class);
    private final DataEntryConverter dataEntryConverter;

    ExtraDataConverter(DataEntryConverter dataEntryConverter) {
        this.dataEntryConverter = dataEntryConverter;
    }

    public static ExtraDataConverter newInstance() {
        return new ExtraDataConverter(
                DataEntryConverter.newInstance()
        );
    }

    public ExtraData read(byte[] extraDataBytes) throws ExtraDataException {
        if (extraDataBytes == null || extraDataBytes.length == 0) {
            return new SimpleExtraData();
        }

        ExtraDataProto.ExtraData extraData = parseProto(extraDataBytes);
        List<Object> dataEntryList = parseDataEntries(extraData);

        return new SimpleExtraData(dataEntryList);
    }

    private ExtraDataProto.ExtraData parseProto(byte[] extraDataBytes) throws ExtraDataException {
        try {
            return ExtraDataProto.ExtraData.parseFrom(extraDataBytes);
        } catch (InvalidProtocolBufferException e) {
            throw new ExtraDataException("Cannot parse extra data", e);
        }
    }

    private List<Object> parseDataEntries(ExtraDataProto.ExtraData extraData) {
        List<Object> parsedDataEntries = new ArrayList<>();
        for (DataEntryProto.DataEntry dataEntry : extraData.getListList()) {
            try {
                parsedDataEntries.add(dataEntryConverter.convertDataEntry(dataEntry));
            } catch (ExtraDataException e) {
                LOG.error("Failed to parse data entry: {}", e.getMessage());
            }
        }

        return parsedDataEntries;
    }


}
