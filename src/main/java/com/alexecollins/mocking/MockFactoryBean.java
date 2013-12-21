package com.alexecollins.mocking;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MockFactoryBean implements FactoryBean {
    private final Object object;

    public MockFactoryBean(Class clazz) {

        object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Exception {
                return method.getReturnType().newInstance();
            }
        });
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
