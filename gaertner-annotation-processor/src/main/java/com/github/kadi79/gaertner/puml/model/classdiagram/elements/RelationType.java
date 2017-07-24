package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

/**
 * <p>RelationType class.</p>
 *
 * @since 0.0.1
 */
public enum RelationType {
	EXTENSION("<|-[#0000ff]-"), AGGREGATION("o-[#a0ff00]-"), COMPOSITION("*-[#ffa000]-");
	
	private String arrowString;
	
	private RelationType(String arrowHead) {
		this.arrowString = arrowHead;
	}
	
	/**
	 * <p>Getter for the field <code>arrowString</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getArrowString() {
		return arrowString;
	}
}
