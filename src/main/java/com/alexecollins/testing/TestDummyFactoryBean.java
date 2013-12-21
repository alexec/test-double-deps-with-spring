package com.alexecollins.testing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestDummyFactoryBean extends AbstractTestDoubleFactoryBean {

    public TestDummyFactoryBean(Class clazz) {
        super(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) {
                return Defaults.get(method.getReturnType());
            }
        }));
    }
}
