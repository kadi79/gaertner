package org.gaertner.annotationprocessor.puml.model.classdiagram.elements;

public class Field extends TypedElement {

	private String name;
	private Class enclosingElement;
	
	public Field(String type, String name) {
		super(type);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public Class getEnclosingElement() {
		return enclosingElement;
	}
	
	void setEnclosingElement(Class enclosingElement) {
		this.enclosingElement = enclosingElement;
	}
}
