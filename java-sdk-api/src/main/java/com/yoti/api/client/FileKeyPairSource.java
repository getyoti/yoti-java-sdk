package com.yoti.api.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Responsible for getting key pairs from a given {@link File} instance.
 *
 */
public final class FileKeyPairSource extends AutoClosingKeyPairSource {
    private final InputStream stream;
    private final String asString;

    public static FileKeyPairSource fromFile(File file) throws InitialisationException {
        return new FileKeyPairSource(file);
    }

    private FileKeyPairSource(File file) throws InitialisationException {
        this.asString = "file: " + file.getAbsolutePath();
        try {
            stream = new FileInputStream(file);
        } catch (IOException e) {
            throw new InitialisationException("Cannot open keypair from file: " + file, e);
        }
    }

    @Override
    protected InputStream getStream() {
        return stream;
    }

    @Override
    public String toString() {
        return asString;
    }
}
