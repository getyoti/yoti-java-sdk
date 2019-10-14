package com.yoti.api.client.spi.remote;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yoti.api.client.AttributeIssuanceDetails;
import com.yoti.api.client.ExtraDataException;
import com.yoti.api.client.spi.remote.proto.DataEntryProto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataEntryConverterTest {

    private static final String SOME_DATA_ENTRY = "someBytes";
    private static final ByteString SOME_DATA_ENTRY_BYTE_STRING = ByteString.copyFromUtf8(SOME_DATA_ENTRY);

    @InjectMocks
    DataEntryConverter dataEntryConverter;

    @Mock
    ThirdPartyAttributeConverter thirdPartyAttributeConverterMock;
    @Mock
    DataEntryProto.DataEntry dataEntryMock;
    @Mock
    AttributeIssuanceDetails attributeIssuanceDetailsMock;

    @Test
    public void shouldParseDataEntryCorrectly() throws Exception {
        when(dataEntryMock.getType()).thenReturn(DataEntryProto.DataEntry.Type.THIRD_PARTY_ATTRIBUTE);
        when(dataEntryMock.getValue()).thenReturn(SOME_DATA_ENTRY_BYTE_STRING);
        when(thirdPartyAttributeConverterMock.parseThirdPartyAttribute(eq(SOME_DATA_ENTRY_BYTE_STRING.toByteArray()))).thenReturn(attributeIssuanceDetailsMock);

        dataEntryConverter.convertDataEntry(dataEntryMock);
    }

    @Test
    public void shouldThrowExceptionForUnsupportedProtobufType() {
        when(dataEntryMock.getType()).thenReturn(DataEntryProto.DataEntry.Type.UNDEFINED);

        try {
            dataEntryConverter.convertDataEntry(dataEntryMock);
        } catch (ExtraDataException e) {
            assertThat(e.getMessage(), containsString("Unsupported/invalid data entry"));
            return;
        }
        fail("Expected ExtraDataException");
    }

    @Test(expected = ExtraDataException.class)
    public void shouldWrapInvalidProtocolBufferException() throws Exception {
        InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException("Some Exception");

        when(dataEntryMock.getType()).thenReturn(DataEntryProto.DataEntry.Type.THIRD_PARTY_ATTRIBUTE);
        when(dataEntryMock.getValue()).thenReturn(SOME_DATA_ENTRY_BYTE_STRING);
        when(thirdPartyAttributeConverterMock.parseThirdPartyAttribute(SOME_DATA_ENTRY_BYTE_STRING.toByteArray())).thenThrow(invalidProtocolBufferException);

        dataEntryConverter.convertDataEntry(dataEntryMock);
    }

}
