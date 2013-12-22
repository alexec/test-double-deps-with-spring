package com.alexecollins.testing;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alex.collins
 */
public class DoubleManagerTestExecutionListener extends AbstractTestExecutionListener {
    private static class Target {
        final Object o;
        final Method m;

        private Target(Object o, Method m) {
            this.o = o;
            this.m = m;
        }
    }

    private List<Target> targets;

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        if (targets == null) {
            targets = new ArrayList<Target>();
            for (Object o : testContext.getApplicationContext().getBeansOfType(Object.class).values()) {
                for (Method m : o.getClass().getMethods()) {
                    if (AnnotationUtils.findAnnotation(m, Before.class) != null) {
                        targets.add(new Target(o, m));
                    }
                }
            }
        }
        for (Target target : targets) {
            target.m.invoke(target.o);
        }
    }
}
