package com.alexecollins.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author alex.collins
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MockFactoryBeanTest {
    @Autowired
    Example example;

    @Test
    public void testExample() throws Exception {
        when(example.intValue()).thenReturn(1);

        assertEquals(1, example.intValue());

    }
}
