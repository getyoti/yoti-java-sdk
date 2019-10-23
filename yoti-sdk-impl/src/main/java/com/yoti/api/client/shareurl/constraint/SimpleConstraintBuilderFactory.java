package com.yoti.api.client.shareurl.constraint;

public class SimpleConstraintBuilderFactory extends ConstraintBuilderFactory {

    @Override
    protected ConstraintBuilderFactory createConstraintBuilderFactory() {
        return new SimpleConstraintBuilderFactory();
    }

    @Override
    public SourceConstraintBuilder createSourceConstraintBuilder() {
        return new SimpleSourceConstraintBuilder();
    }

}
