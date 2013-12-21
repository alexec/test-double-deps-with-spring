package com.alexecollins.testing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alex.collins
 */
public class DoubleManager {
    private static class Target {
        final Object o;
        final Method m;

        private Target(Object o, Method m) {
            this.o = o;
            this.m = m;
        }
    }

    private final List<Target> targets = new ArrayList<Target>();

    @Autowired
    public DoubleManager(Object[] objects) {
        for (Object o : objects) {
            for (Method m : o.getClass().getMethods()) {
                if (AnnotationUtils.findAnnotation(m, Before.class) != null) {
                    targets.add(new Target(o, m));
                }
            }
        }
    }

    public void setUp() throws Exception {
        for (Target target : targets) {
            target.m.invoke(target.o);
        }
    }
}
