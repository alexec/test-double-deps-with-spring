package com.alexecollins.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext-TestDummyFactoryBeanTest.xml")
public class TestDummyFactoryBeanTest {

    @Autowired
    Example example;

    @Test
    public void voidValue() {
        example.voidValue();
    }

    @Test
    public void intValue() {
        assertEquals(0, example.intValue());
    }

    @Test
    public void longValue() {
        assertEquals(0l, example.longValue());
    }

    @Test
    public void floatValue() {
        assertEquals(0f, example.floatValue(), 0);
    }

    @Test
    public void doubleValue() {
        assertEquals(0.0, example.doubleValue(), 0);
    }

    @Test
    public void byteValue() {
        assertEquals((byte)0, example.byteValue());
    }

    @Test
    public void charValue() {
        assertEquals((char)0, example.charValue());
    }

    @Test
    public void shortValue() {
        assertEquals((short)0, example.shortValue());
    }

    @Test
    public void booleanValue() {
        assertEquals(false, example.booleanValue());
    }

    @Test
    public void objectValue() {
        assertNull(example.objectValue());
    }

    @Test
    public void arrayValue() {
        assertArrayEquals(new Object[0], example.arrayValue());
    }

}
