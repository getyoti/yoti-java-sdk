package com.yoti.api.client.spi.remote;

import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.yoti.api.attributes.AttributeConstants.HumanProfileAttributes;
import com.yoti.api.client.Anchor;
import com.yoti.api.client.Attribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddressTransformerTest {

    private static final String SOME_FORMATTED_ADDRESS = "someFormattedAddress";

    @InjectMocks AddressTransformer testObj;

    @Mock Attribute<Map> structuredAddressMock;
    @Mock Attribute<Object> badAttributeMock;
    @Mock Anchor sourceAnchorMock;
    @Mock Anchor verifierAnchorMock;

    @Test
    public void shouldReturnNullWhenAddressValueIsNull() {
        Attribute<String> result = testObj.transform(structuredAddressMock);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void shouldReturnNullWhenFormattedAddressIsNull() {
        when(structuredAddressMock.getValue()).thenReturn(new HashMap());

        Attribute<String> result = testObj.transform(structuredAddressMock);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void shouldReturnNullWhenTheresAnException() {
        when(badAttributeMock.getValue()).thenReturn(new Object());

        Attribute<String> result = testObj.transform(badAttributeMock);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void shouldReturnTransformedAddress() {
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put(HumanProfileAttributes.Keys.FORMATTED_ADDRESS, SOME_FORMATTED_ADDRESS);
        when(structuredAddressMock.getValue()).thenReturn(addressMap);
        when(structuredAddressMock.getSources()).thenReturn(asList(sourceAnchorMock));
        when(structuredAddressMock.getVerifiers()).thenReturn(asList(verifierAnchorMock));
        when(structuredAddressMock.getAnchors()).thenReturn(asList(sourceAnchorMock, verifierAnchorMock));

        Attribute<String> result = testObj.transform(structuredAddressMock);

        assertThat(result.getName(), is(HumanProfileAttributes.POSTAL_ADDRESS));
        assertThat(result.getValue(), is(SOME_FORMATTED_ADDRESS));
        assertThat(result.getSources(), hasItems(sourceAnchorMock));
        assertThat(result.getVerifiers(), hasItems(verifierAnchorMock));
        assertThat(result.getAnchors(), hasItems(sourceAnchorMock, verifierAnchorMock));
    }

}
