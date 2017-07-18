package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

/**
 * <p>RelationType class.</p>
 *
 * @since 0.0.1
 */
public enum RelationType {
	EXTENSION("<|"), AGGREGATION("o"), COMPOSITION("*");
	
	private String arrowHead;
	
	private RelationType(String arrowHead) {
		this.arrowHead = arrowHead;
	}
	
	/**
	 * <p>Getter for the field <code>arrowHead</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getArrowHead() {
		return arrowHead;
	}
}
