package com.alexecollins.testing;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestDummyFactoryBean implements FactoryBean {


    private final Object object;

    public TestDummyFactoryBean(Class clazz) {

        object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Exception {
                final Class<?> returnType = method.getReturnType();
                return returnType.isPrimitive() ? Defaults.get(returnType) :
                        returnType.isArray() ? new Object[0] : null;
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
