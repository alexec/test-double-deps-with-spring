package com.alexecollins.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author alex.collins
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext-PartialFakeFactoryBeanTest.xml")
public class PartialFakeFactoryBeanTest {

    @Autowired
    @Qualifier("example1")
    private PartialFakeExample example1;
    @Autowired
    private PartialFakeExample2 example2;

    @Test
    public void longValue() throws Exception {
        assertEquals(1l, example1.longValue());
    }

    @Test
    public void intValue() throws Exception {
        assertEquals(1, example2.intValue());
    }

    @Test
    public void floatValue() throws Exception {
        assertEquals(1f, example2.floatValue(), 0);
    }

    @Test
    public void doubleValue() throws Exception {
        assertEquals(0d, example2.doubleValue(), 0);
    }
}
