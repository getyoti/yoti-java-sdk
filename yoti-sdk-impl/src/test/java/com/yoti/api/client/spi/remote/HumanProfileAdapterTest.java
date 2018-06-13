package com.yoti.api.client.spi.remote;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.yoti.api.client.Attribute;
import com.yoti.api.client.Profile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HumanProfileAdapterTest {

    private static final String ATTR_AGE_OVER = "age_over:";
    private static final String ATTR_AGE_UNDER = "age_under:";

    @InjectMocks HumanProfileAdapter testObj;

    @Mock Profile profileMock;

    @Test
    public void isAgeVerified_shouldReturnNullWhenBothAttributesAreMissing() {
        Boolean result = testObj.isAgeVerified();

        assertNull(result);
    }

    @Test
    public void isAgeVerified_shouldReturnValueOfAgeOver() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_OVER, String.class)).thenReturn(new Attribute(ATTR_AGE_OVER, "true"));

        Boolean result = testObj.isAgeVerified();

        assertTrue(result);
    }

    @Test
    public void isAgeVerified_shouldReturnValueOfAgeUnder() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_UNDER, String.class)).thenReturn(new Attribute(ATTR_AGE_UNDER, "false"));

        Boolean result = testObj.isAgeVerified();

        assertFalse(result);
    }

    // This scenario should never happen, but to be pragmatic...
    @Test
    public void isAgeVerified_shouldPreferAgeOverWhenBothAttributesArePresent() {
        when(profileMock.findAttributeStartingWith(ATTR_AGE_OVER, String.class)).thenReturn(new Attribute(ATTR_AGE_OVER, "true"));
        when(profileMock.findAttributeStartingWith(ATTR_AGE_UNDER, String.class)).thenReturn(new Attribute(ATTR_AGE_UNDER, "false"));

        Boolean result = testObj.isAgeVerified();

        assertTrue(result);
    }

}
