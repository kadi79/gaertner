package org.gaertner.annotationprocessor.puml.model.classdiagram.elements;

public class Field extends TypedElement {

	private String name;
	private Class enclosingElement;
	private Visibility visibility;
	
	public Field(String type, String name, Visibility visibility) {
		super(type);
		this.name = name;
		this.visibility = visibility;
	}

	public String getName() {
		return name;
	}
	
	public Visibility getVisibility() {
		return visibility;
	}
	
	public Class getEnclosingElement() {
		return enclosingElement;
	}
	
	void setEnclosingElement(Class enclosingElement) {
		this.enclosingElement = enclosingElement;
	}
}
