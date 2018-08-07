package com.yoti.api.client.spi.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;
import com.yoti.api.client.spi.remote.call.YotiConstants;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class KeyStreamVisitor implements KeyPairSource.StreamVisitor {

    @Override
    public KeyPair accept(InputStream stream) throws IOException, InitialisationException {
        PEMParser reader = new PEMParser(new BufferedReader(new InputStreamReader(stream, YotiConstants.DEFAULT_CHARSET)));
        KeyPair keyPair = findKeyPair(reader);
        if (keyPair == null) {
            throw new InitialisationException("No key pair found in the provided source");
        }
        return keyPair;
    }

    KeyPair findKeyPair(PEMParser reader) throws IOException {
        for (Object o; (o = reader.readObject()) != null; ) {
            if (o instanceof PEMKeyPair) {
                return new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) o);
            }
        }
        return null;
    }

}
