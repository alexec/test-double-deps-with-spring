package com.alexecollins.testing;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class TestDummyFactoryBean implements FactoryBean {
    private static final Map<Class<?>, Object> DEFAULTS = new HashMap<Class<?>, Object>();

    static {
        DEFAULTS.put(boolean.class, false);
        DEFAULTS.put(char.class, '\0');
        DEFAULTS.put(byte.class, (byte) 0);
        DEFAULTS.put(short.class, (short) 0);
        DEFAULTS.put(int.class, 0);
        DEFAULTS.put(long.class, 0L);
        DEFAULTS.put(float.class, 0f);
        DEFAULTS.put(double.class, 0d);
    }

    private final Object object;

    public TestDummyFactoryBean(Class clazz) {

        object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Exception {
                final Class<?> returnType = method.getReturnType();
                return returnType.isPrimitive() ? DEFAULTS.get(returnType) :
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
