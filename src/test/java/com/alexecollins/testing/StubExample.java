package com.alexecollins.testing;

/**
 * @author alex.collins
 */
public class StubExample implements Example {

    @Override
    public void voidValue() {
    }

    @Override
    public int intValue() {
        return 1;
    }

    @Override
    public long longValue() {
        return 0;
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }

    @Override
    public byte byteValue() {
        return 0;
    }

    @Override
    public char charValue() {
        return 0;
    }

    @Override
    public short shortValue() {
        return 0;
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
