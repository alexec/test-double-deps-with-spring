package com.alexecollins.testing;

/**
 * @author alex.collins
 */
public abstract class PartialFakeExample implements Example {

    @Override
    public long longValue() {return 1l;}

    @Override
    public int intValue() {return 1;}
}
