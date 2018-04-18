package com.yoti.api.client.sandbox;

import static org.apache.commons.codec.binary.Hex.DEFAULT_CHARSET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;

import com.yoti.api.client.InitialisationException;
import com.yoti.api.client.KeyPairSource;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class KeyPairLoader {

    public static KeyPair loadKeyPair(KeyPairSource kpSource) throws InitialisationException {
        try {
            return kpSource.getFromStream(new KeyStreamVisitor());
        } catch (IOException e) {
            throw new InitialisationException("Cannot load key pair", e);
        }
    }

    private static class KeyStreamVisitor implements KeyPairSource.StreamVisitor {

        @Override
        public KeyPair accept(InputStream stream) throws IOException, InitialisationException {
            PEMParser reader = new PEMParser(new BufferedReader(new InputStreamReader(stream, DEFAULT_CHARSET)));
            KeyPair keyPair = findKeyPair(reader);
            if (keyPair == null) {
                throw new InitialisationException("No key pair found in the provided source");
            }
            return keyPair;
        }

        private KeyPair findKeyPair(PEMParser reader) throws IOException {
            KeyPair keyPair = null;
            for (Object o; (o = reader.readObject()) != null; ) {
                if (o instanceof PEMKeyPair) {
                    keyPair = new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) o);
                    break;
                }
            }
            return keyPair;
        }
    }

}
