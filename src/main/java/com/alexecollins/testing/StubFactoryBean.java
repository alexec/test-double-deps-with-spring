package com.alexecollins.testing;

/**
 * @author alex.collins
 */
public class StubFactoryBean extends AbstractTestDoubleFactoryBean {
    public StubFactoryBean(Class clazz) throws Exception {
        super(clazz.newInstance());
    }
}
