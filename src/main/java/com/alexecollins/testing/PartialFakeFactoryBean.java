package com.alexecollins.testing;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author alex.collins
 */
public class PartialFakeFactoryBean extends AbstractTestDoubleFactoryBean {
    protected PartialFakeFactoryBean(Class clazz) {
        super(newObject(clazz));
    }

    private static Object newObject(final Class clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                try {
                    final Method clazzMethod = clazz.getMethod(method.getName(), method.getParameterTypes());
                    if (!Modifier.isAbstract(clazzMethod.getModifiers())) {
                        return proxy.invokeSuper(obj, args);
                    }
                } catch (NoSuchMethodException ignored) {
                }
                return Defaults.get(method.getReturnType());
            }
        });
        return enhancer.create();
    }
}
