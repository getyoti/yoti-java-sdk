package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class PngAttributeValueTest {

    private static final byte[] IMAGE_DATA = new byte[] { 0, 1, 2, 3 };

    PngAttributeValue testObj = new PngAttributeValue(IMAGE_DATA);

    @Test
    public void shouldReturnCopyOfTheData() {
        byte[] result = testObj.getContent();

        assertArrayEquals(IMAGE_DATA, result);
        assertNotSame(IMAGE_DATA, result);
    }

    @Test
    public void shouldReturnBase64Selfie() {
        String result = testObj.getBase64Content();

        String base64Data = Base64.getEncoder().encodeToString(IMAGE_DATA);
        assertEquals("data:image/png;base64," + base64Data, result);
    }

}
