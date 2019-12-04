package com.yoti.api.client.spi.remote.util;

import com.google.protobuf.ByteString;
import com.yoti.api.client.spi.remote.proto.DataEntryProto;
import com.yoti.api.client.spi.remote.proto.ExtraDataProto;
import com.yoti.api.client.spi.remote.proto.IssuingAttributesProto;
import com.yoti.api.client.spi.remote.proto.ThirdPartyAttributeProto;

import java.util.Arrays;

public class ProtoUtil {

    public static IssuingAttributesProto.Definition buildDefinition(String name) {
        return IssuingAttributesProto.Definition.newBuilder()
                .setName(name).build();
    }

    public static IssuingAttributesProto.IssuingAttributes buildIssuingAttributes(String expiryDate, IssuingAttributesProto.Definition... definitions) {
        IssuingAttributesProto.IssuingAttributes.Builder builder = IssuingAttributesProto.IssuingAttributes.newBuilder();

        builder.setExpiryDate(expiryDate);
        for (IssuingAttributesProto.Definition definition : Arrays.asList(definitions)) {
            builder.addDefinitions(definition);
        }

        return builder.build();
    }

    public static ThirdPartyAttributeProto.ThirdPartyAttribute buildThirdPartyAttribute(String token, IssuingAttributesProto.IssuingAttributes issuingAttributes) {
        ThirdPartyAttributeProto.ThirdPartyAttribute.Builder builder = ThirdPartyAttributeProto.ThirdPartyAttribute.newBuilder();
        if (issuingAttributes != null) {
            builder.setIssuingAttributes(issuingAttributes);
        }

        builder.setIssuanceToken(ByteString.copyFromUtf8(token));
        return builder.build();
    }

    public static DataEntryProto.DataEntry buildDataEntry(DataEntryProto.DataEntry.Type type, ByteString value) {
        return DataEntryProto.DataEntry.newBuilder()
                .setType(type)
                .setValue(value)
                .build();
    }

    public static ExtraDataProto.ExtraData buildExtraData(DataEntryProto.DataEntry... dataEntries) {
        ExtraDataProto.ExtraData.Builder builder = ExtraDataProto.ExtraData.newBuilder();
        for (DataEntryProto.DataEntry dataEntry : Arrays.asList(dataEntries)) {
            builder.addList(dataEntry);
        }

        return builder.build();
    }

}
