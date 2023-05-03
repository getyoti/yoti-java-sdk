package com.yoti.api.client.spi.remote;

import static com.yoti.api.client.spi.remote.AttributeConverter.newInstance;

import java.io.IOException;
import java.text.ParseException;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.spi.remote.proto.AttributeProto;

public class AttributeParser {

    static AttributeConverter converter = newInstance();

    public static Attribute<?> fromProto(AttributeProto.Attribute proto) throws ParseException, IOException {
        return converter.convertAttribute(proto);
    }

}
