package com.alexecollins.testing;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * @author alex.collins
 */
public class DoubleManagerTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        ((DoubleManager) testContext.getApplicationContext().getBeansOfType(DoubleManager.class).values().iterator().next()).setUp();
    }
}
