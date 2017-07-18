package com.github.kadi79.gaertner.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>UmlClassDiagrams class.</p>
 *
 * @since 0.0.1
 */
@Retention(SOURCE)
@Target({TYPE})
public @interface UmlClassDiagrams {

	UmlClassDiagram[] value();

}
