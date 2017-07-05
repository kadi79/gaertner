package org.gaertner.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target({TYPE})
public @interface UmlClassDiagram {
	String filename();
	Visibility[] fields() default {Visibility.PUBLIC, Visibility.PACKAGE_PRIVATE, Visibility.PROTECTED, Visibility.PRIVATE};
	Visibility[] methods() default {Visibility.PUBLIC, Visibility.PACKAGE_PRIVATE};
}
