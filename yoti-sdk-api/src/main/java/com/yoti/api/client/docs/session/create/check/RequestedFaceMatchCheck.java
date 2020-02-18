package com.yoti.api.client.docs.session.create.check;

/**
 * Requests creation of a FaceMatchCheck
 *
 * @param <T> class extending {@link RequestedFaceMatchConfig}
 */
public interface RequestedFaceMatchCheck<T extends RequestedFaceMatchConfig> extends RequestedCheck<T> {

    @Override
    T getConfig();

}
