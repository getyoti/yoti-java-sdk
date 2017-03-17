package com.yoti.api.spring;

import com.yoti.api.client.KeyPairSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpringResourceKeyPairSourceTest {

    private static final int DUMMY_BYTE = 4;

    private final KeyPair dummyKeyPair = new KeyPair(null, null);

    @Mock
    private Resource mockResource;

    @Mock
    private InputStream mockStream;

    @Test
    public void testGetFromStream() throws Exception {

        when(mockResource.getInputStream()).thenReturn(mockStream);
        when(mockStream.read()).thenReturn(DUMMY_BYTE);

        final SpringResourceKeyPairSource source = new SpringResourceKeyPairSource(mockResource);
        final KeyPair keyPair = source.getFromStream(new TestStreamVisitor());

        assertThat(keyPair, sameInstance(dummyKeyPair));
        verify(mockStream).close();
        verifyNoMoreInteractions(mockStream);
    }

    private class TestStreamVisitor implements KeyPairSource.StreamVisitor {

        @Override
        public KeyPair accept(final InputStream in) throws IOException {
            return dummyKeyPair;
        }

    }
}
