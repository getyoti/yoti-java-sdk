package com.yoti.api.client.docs.session.create;

public class SimpleSessionSpecBuilderFactory extends SessionSpecBuilderFactory {

    @Override
    public SessionSpecBuilder create() {
        return new SimpleSessionSpecBuilder();
    }

}
