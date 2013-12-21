package com.alexecollins.testing;

import java.lang.annotation.*;

/**
 * @author alex.collins
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
}
