package com.alexecollins.testing;

import org.mockito.Mockito;

/**
 * @author alex.collins
 */
public class MockFactoryBean extends AbstractTestDoubleFactoryBean {
    public MockFactoryBean(Class clazz) {
        super(Mockito.mock(clazz));
    }
}
