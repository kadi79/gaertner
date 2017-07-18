package com.github.kadi79.gaertner.annotations;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>ReferenceType class.</p>
 *
 * @since 0.0.1
 */
@Retention(RetentionPolicy.SOURCE)
@Target({FIELD})
public @interface ReferenceType {
	String value();
}
