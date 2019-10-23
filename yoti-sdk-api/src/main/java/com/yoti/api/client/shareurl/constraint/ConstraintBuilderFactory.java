package com.yoti.api.client.shareurl.constraint;

import com.yoti.api.client.shareurl.extension.ExtensionBuilderFactory;

import java.util.ServiceLoader;

public abstract class ConstraintBuilderFactory {

    public static final ConstraintBuilderFactory newInstance() {
        ServiceLoader<ConstraintBuilderFactory> constraintBuilderFactoryServiceLoader = ServiceLoader.load(ConstraintBuilderFactory.class);
        if (!constraintBuilderFactoryServiceLoader.iterator().hasNext()) {
            throw new IllegalStateException("Cannot find any implementation of " + ExtensionBuilderFactory.class.getSimpleName());
        }
        ConstraintBuilderFactory constraintBuilderFactory = constraintBuilderFactoryServiceLoader.iterator().next();
        return constraintBuilderFactory.createConstraintBuilderFactory();
    }

    protected abstract ConstraintBuilderFactory createConstraintBuilderFactory();

    public abstract SourceConstraintBuilder createSourceConstraintBuilder();

}
