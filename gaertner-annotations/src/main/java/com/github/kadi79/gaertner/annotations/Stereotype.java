package com.github.kadi79.gaertner.annotations;

import static com.github.kadi79.gaertner.annotations.Stereotype.HighlightType.SPECIFIED;

/**
 * <p>Stereotype class.</p>
 *
 * @since 0.0.2
 */
public @interface Stereotype {
	public static enum HighlightType {
		DEFAULT, SPECIFIED
	}
	String type() default "";
	HighlightType highlightType() default SPECIFIED;
	char highlightChar() default 'C';
	String highlightColor() default "#add1b2";
}
