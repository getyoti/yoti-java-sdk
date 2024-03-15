package com.yoti.api.client.shareurl.policy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.yoti.api.client.shareurl.constraint.Constraint;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class WantedAttributeTest {

    public static final String SOME_NAME = "someName";
    public static final String SOME_DERIVATION = "someDerivation";

    @Mock Constraint constraintMock;

    @Test
    public void buildsAnAttribute() {
        WantedAttribute result = WantedAttribute.builder()
                .withName(SOME_NAME)
                .withDerivation(SOME_DERIVATION)
                .withOptional(true)
                .withAcceptSelfAsserted(true)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertEquals(SOME_DERIVATION, result.getDerivation());
        assertTrue(result.isOptional());
        assertTrue(result.getAcceptSelfAsserted());
    }

    @Test
    public void buildsAnAttributeWithSourceConstraints() {
        List<Constraint> constraints = new ArrayList<>();
        constraints.add(constraintMock);

        WantedAttribute result = WantedAttribute.builder()
                .withName(SOME_NAME)
                .withConstraints(constraints)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertThat(result.getConstraints(), hasSize(1));
        assertTrue(result.getConstraints().contains(constraintMock));
    }

    @Test
    public void buildsAnAttributeWithSourceConstraint() {
        WantedAttribute result = WantedAttribute.builder()
                .withName(SOME_NAME)
                .withConstraint(constraintMock)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertThat(result.getConstraints(), hasSize(1));
        assertTrue(result.getConstraints().contains(constraintMock));
    }

    @Test
    public void buildsAnAttributeWithSingleAndMultipleSourceConstraint() {
        List<Constraint> constraints = new ArrayList<>();
        constraints.add(constraintMock);

        Constraint extraConstraintMock = mock(Constraint.class);

        WantedAttribute result = WantedAttribute.builder()
                .withName(SOME_NAME)
                .withConstraints(constraints)
                .withConstraint(extraConstraintMock)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertThat(result.getConstraints(), hasSize(2));
        assertTrue(result.getConstraints().contains(constraintMock));
        assertTrue(result.getConstraints().contains(extraConstraintMock));
    }

    @Test
    public void buildsAnAttributeWithAlternativeNames() {
        String anAlternativeName = "anAlternativeName";
        HashSet<String> alternativeNames = new HashSet<>();
        alternativeNames.add(anAlternativeName);

        WantedAttribute result = WantedAttribute.builder()
                .withName(SOME_NAME)
                .withAlternativeNames(alternativeNames)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertThat(result.getAlternativeNames(), hasSize(1));
        assertTrue(result.getAlternativeNames().contains(anAlternativeName));
    }

    @Test
    public void buildsAnAttributeWithAlternativeName() {
        String anAlternativeName = "anAlternativeName";
        WantedAttribute result = WantedAttribute.builder()
                .withName(SOME_NAME)
                .withAlternativeName(anAlternativeName)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertThat(result.getAlternativeNames(), hasSize(1));
        assertTrue(result.getAlternativeNames().contains(anAlternativeName));
    }

    @Test
    public void buildsAnAttributeWithSingleAndMultipleAlternativeNames() {
        String anAlternativeName = "anAlternativeName";
        String anExtraAlternativeName = "anExtraAlternativeName";
        HashSet<String> alternativeNames = new HashSet<>();
        alternativeNames.add(anAlternativeName);

        WantedAttribute result = WantedAttribute.builder()
                .withName(SOME_NAME)
                .withAlternativeNames(alternativeNames)
                .withAlternativeName(anExtraAlternativeName)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertThat(result.getAlternativeNames(), hasSize(2));
        assertTrue(result.getAlternativeNames().contains(anAlternativeName));
        assertTrue(result.getAlternativeNames().contains(anExtraAlternativeName));
    }

    @Test
    public void buildingAnAttributeWithoutNameFails() {
        try { 
            WantedAttribute.builder().build();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("name"));
            return;
        }
        fail("Expected an Exception");
    }

}
