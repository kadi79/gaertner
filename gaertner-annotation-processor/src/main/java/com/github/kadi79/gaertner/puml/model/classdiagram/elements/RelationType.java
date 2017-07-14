package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

public enum RelationType {
	EXTENSION("<|"), AGGREGATION("o"), COMPOSITION("*");
	
	private String arrowHead;
	
	private RelationType(String arrowHead) {
		this.arrowHead = arrowHead;
	}
	
	public String getArrowHead() {
		return arrowHead;
	}
}