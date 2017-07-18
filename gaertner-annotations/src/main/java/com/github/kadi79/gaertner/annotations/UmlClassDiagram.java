package com.github.kadi79.gaertner.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>UmlClassDiagram class.</p>
 *
 * @since 0.0.1
 */
@Repeatable(UmlClassDiagrams.class)
@Retention(SOURCE)
@Target({TYPE})
public @interface UmlClassDiagram {
	String filename();
	Visibility[] fields() default {Visibility.PUBLIC, Visibility.PACKAGE_PRIVATE, Visibility.PROTECTED, Visibility.PRIVATE};
	Visibility[] methods() default {Visibility.PUBLIC, Visibility.PACKAGE_PRIVATE};
}
