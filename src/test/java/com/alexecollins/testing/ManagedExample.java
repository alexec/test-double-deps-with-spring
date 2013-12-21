package com.alexecollins.testing;

/**
 * @author alex.collins
 */
public class ManagedExample implements Example {
    private int value;

    @Before
    public void setUp() {
        value = 1;
    }

    @Override
    public void voidValue() {
        
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public byte byteValue() {
        return '\0';
    }

    @Override
    public char charValue() {
        return '\0';
    }

    @Override
    public short shortValue() {
        return (short)value;
    }

    @Override
    public boolean booleanValue() {
        return false;
    }

    @Override
    public Object objectValue() {
        return null;
    }

    @Override
    public Object[] arrayValue() {
        return new Object[0];
    }
}
