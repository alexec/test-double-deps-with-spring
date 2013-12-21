package com.alexecollins.mocking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext-MockFactoryBeanTest.xml")
public class MockFactoryBeanTest {

    @Autowired
    Runnable runnable;

    @Test
    public void testRunnable() throws Exception {

        assertNotNull(runnable);

    }
}
