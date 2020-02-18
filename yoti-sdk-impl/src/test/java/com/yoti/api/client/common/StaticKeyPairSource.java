package com.yoti.api.client.common;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;

public class StaticKeyPairSource implements KeyPairSource {

    private String keypair;
    private boolean streamException;
    private boolean sourceException;

    public StaticKeyPairSource(boolean inStreamException) {
        this.streamException = inStreamException;
        this.sourceException = !inStreamException;
    }

    public StaticKeyPairSource(String keypair) {
        this.keypair = keypair;
    }

    @Override
    public KeyPair getFromStream(KeyPairSource.StreamVisitor streamVisitor) throws IOException, InitialisationException {
        if (sourceException) {
            throw new IOException("Test source exception");
        }
        if (streamException) {
            InputStream inputStreamMock = mock(InputStream.class);
            when(inputStreamMock.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException("Test stream exception"));
            return streamVisitor.accept(inputStreamMock);
        } else {
            InputStream is = new ByteArrayInputStream(keypair.getBytes());
            return streamVisitor.accept(is);
        }
    }

}
