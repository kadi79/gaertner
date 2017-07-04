package org.gaertner.annotationprocessor.puml.model.classdiagram.elements;

public enum Visibility {
	PRIVATE("-"),
	PROTECTED("#"),
	PACKAGE_PRIVATE("~"),
	PUBLIC("+");
	
	private String presentation;
	
	private Visibility(String presentation) {
		this.presentation = presentation;
	}
	
	public String getPresentation() {
		return presentation;
	}
}
