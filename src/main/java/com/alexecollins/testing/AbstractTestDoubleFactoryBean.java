package com.alexecollins.testing;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author alex.collins
 */
abstract class AbstractTestDoubleFactoryBean implements FactoryBean {
    private final Object object;

    protected AbstractTestDoubleFactoryBean(Object object) {
        this.object = object;
    }

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public Class getObjectType() {
        return object.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
