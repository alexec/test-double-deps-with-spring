package com.alexecollins.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertEquals;

/**
 * @author alex.collins
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({
        // if we override, we must specify all the ones we want, shame we can't say "please use the defaults"
        DependencyInjectionTestExecutionListener.class,
        DoubleManagerTestExecutionListener.class
})
public class DoubleManagerTest {
    @Autowired
    Example example;

    @Test
    public void testSetUp() throws Exception {
        assertEquals(1, example.intValue());
    }
}
