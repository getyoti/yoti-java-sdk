package com.yoti.api.client.shareurl.policy;

import com.yoti.api.client.shareurl.constraint.Constraint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleWantedAttributeBuilderTest {

    public static final String SOME_NAME = "someName";
    public static final String SOME_DERIVATION = "someDerivation";

    @Mock
    List<Constraint> constraintListMock;

    @Test
    public void buildsAnAttribute() {
        WantedAttribute result = WantedAttributeBuilder.newInstance()
                .withName(SOME_NAME)
                .withDerivation(SOME_DERIVATION)
                .withOptional(true)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertEquals(SOME_DERIVATION, result.getDerivation());
        assertEquals(true, result.isOptional());
    }

    @Test
    public void buildsAnAttributeWithSourceConstraint() {
        when(constraintListMock.size()).thenReturn(1);

        WantedAttribute result = WantedAttributeBuilder.newInstance()
                .withName(SOME_NAME)
                .withDerivation(SOME_DERIVATION)
                .withOptional(true)
                .withConstraints(constraintListMock)
                .withAcceptSelfAsserted(true)
                .build();

        assertEquals(SOME_NAME, result.getName());
        assertEquals(SOME_DERIVATION, result.getDerivation());
        assertEquals(true, result.isOptional());
        assertEquals(true, result.getAcceptSelfAsserted());
        assertThat(result.getConstraints(), hasSize(1));
        assertEquals(result.getConstraints(), constraintListMock);
    }

}
