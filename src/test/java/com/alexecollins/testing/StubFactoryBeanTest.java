package com.alexecollins.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author alex.collins
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext-StubFactoryBeanTest.xml")
public class StubFactoryBeanTest {
    @Autowired
    private Example example;

    @Test
    public void intValue() throws Exception {
        assertEquals(1, example.intValue());
    }
}
