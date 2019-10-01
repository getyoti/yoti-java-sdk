package com.yoti.api.client.docs;

import java.util.ServiceLoader;

import com.yoti.api.client.KeyPairSource;

/**
 * Builder used to create {@link DocScanClient}
 */
public abstract class DocScanClientBuilder {

    public static final DocScanClientBuilder newInstance() {
        ServiceLoader<DocScanClientBuilder> docScanClientBuilders = ServiceLoader.load(DocScanClientBuilder.class);
        if (!docScanClientBuilders.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + DocScanClientBuilder.class.getSimpleName());
        }
        return docScanClientBuilders.iterator().next();
    }

    /**
     * Sets the application ID to be used in the {@code DocScanClient}
     * on the builder
     *
     * @param appId the application id
     * @return the builder
     */
    public abstract DocScanClientBuilder withApplicationId(String appId);

    /**
     * Sets the {@link KeyPairSource} to be used in the {@code DocScanClient}
     * on the builder
     *
     * @param kps the {@code KeyPairSource}
     * @return the builder
     */
    public abstract DocScanClientBuilder withKeyPairSource(KeyPairSource kps);

    /**
     * Builds the {@code DocScanClient} using the values supplied
     * to the builder
     *
     * @return the built {@code DocScanClient}
     */
    public abstract DocScanClient build();

}
