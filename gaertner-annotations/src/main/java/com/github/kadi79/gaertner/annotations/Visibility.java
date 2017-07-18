package com.github.kadi79.gaertner.annotations;

/**
 * <p>Visibility class.</p>
 *
 * @since 0.0.1
 */
public enum Visibility {
	PRIVATE("-"),
	PROTECTED("#"),
	PACKAGE_PRIVATE("~"),
	PUBLIC("+");
	
	private String presentation;
	
	private Visibility(String presentation) {
		this.presentation = presentation;
	}
	
	/**
	 * <p>Getter for the field <code>presentation</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPresentation() {
		return presentation;
	}
}
