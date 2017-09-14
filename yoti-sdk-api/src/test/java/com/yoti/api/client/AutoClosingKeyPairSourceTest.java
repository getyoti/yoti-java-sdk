package com.yoti.api.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.yoti.api.client.KeyPairSource.StreamVisitor;

public class AutoClosingKeyPairSourceTest {

    @Test
    public void shouldCloseStreamUponExceptionWhenAsked() throws IOException, InitialisationException {
        final InputStream inputStream = mock(InputStream.class);
        when(inputStream.read()).thenThrow(new IOException("test exception"));

        AutoClosingKeyPairSource source = new AutoClosingKeyPairSource() {
            @Override
            protected InputStream getStream() {
                return inputStream;
            }
        };
        StreamVisitor streamVisitor = mock(StreamVisitor.class);
        when(streamVisitor.accept(Mockito.<InputStream> any())).thenThrow(new IOException("test exception"));
        try {
            source.getFromStream(streamVisitor);
            Assert.fail("IOException expected");
        } catch (IOException ioe) {
            verify(inputStream).close();
        }
    }

    @Test
    public void shouldNotCloseStreamUponExceptionWhenNotAsked() throws IOException, InitialisationException {
        final InputStream inputStream = mock(InputStream.class);
        when(inputStream.read()).thenThrow(new IOException("test exception"));

        AutoClosingKeyPairSource source = new AutoClosingKeyPairSource() {
            @Override
            protected InputStream getStream() {
                return inputStream;
            }

            @Override
            protected boolean shouldClose() {
                return false;
            }
        };
        StreamVisitor streamVisitor = mock(StreamVisitor.class);
        when(streamVisitor.accept(Mockito.<InputStream> any())).thenThrow(new IOException("test exception"));
        try {
            source.getFromStream(streamVisitor);
            Assert.fail("IOException expected");
        } catch (IOException ioe) {
            verify(inputStream, times(0)).close();
        }
    }
}
