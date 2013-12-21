package com.alexecollins.testing;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alex.collins
 */
class Defaults {
    static final Map<Class<?>, Object> DEFAULTS = new HashMap<Class<?>, Object>();

    static {
        DEFAULTS.put(boolean.class, false);
        DEFAULTS.put(char.class, '\0');
        DEFAULTS.put(byte.class, (byte) 0);
        DEFAULTS.put(short.class, (short) 0);
        DEFAULTS.put(int.class, 0);
        DEFAULTS.put(long.class, 0L);
        DEFAULTS.put(float.class, 0f);
        DEFAULTS.put(double.class, 0d);
    }

    static Object get(Class<?> type) {
        return DEFAULTS.get(type);
    }
}
