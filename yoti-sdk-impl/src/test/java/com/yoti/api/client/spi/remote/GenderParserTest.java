package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.yoti.api.client.HumanProfile;

import org.junit.Test;

public class GenderParserTest {

    private static final String GENDER_MALE = "MALE";
    private static final String GENDER_INVALID = "X";

    GenderParser testObj = new GenderParser();

    @Test
    public void shouldReturnGender() {
        HumanProfile.Gender result = testObj.parseFrom(GENDER_MALE);

        assertEquals(HumanProfile.Gender.MALE, result);
    }

    @Test
    public void shouldNotReturnGenderForNullAttribute() {
        HumanProfile.Gender result = testObj.parseFrom(null);

        assertNull(result);
    }

    @Test
    public void shouldNotReturnGenderForInvalidAttribute() {
        HumanProfile.Gender result = testObj.parseFrom(GENDER_INVALID);

        assertEquals(HumanProfile.Gender.OTHER, result);
    }

}
