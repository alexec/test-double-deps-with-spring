package com.alexecollins.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNotNull;

/**
 * @author alex.collins
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext-TestNamespaceHandlerTest.xml")
public class TestNamespaceHandlerTest {

    @Autowired
    Example example;
    @Autowired
    Example2 example2;
    @Test
    public void testName() throws Exception {
        assertNotNull(example);
        assertNotNull(example2);
        assertNotNull(example2.example);

    }
}
