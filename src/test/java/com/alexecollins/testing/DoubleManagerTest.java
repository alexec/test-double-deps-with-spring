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
@ContextConfiguration(locations = "applicationContext-DoubleManagerTest.xml")
public class DoubleManagerTest {
    @Autowired
    DoubleManager doubleManager;
    @Autowired
    Example example;

    @org.junit.Before
    public void setUp() throws Exception {
        doubleManager.setUp();
    }

    @Test
    public void testSetUp() throws Exception {
        assertEquals(1, example.intValue());
    }
}
