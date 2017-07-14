package org.gaertner.annotationprocessor.puml.model.classdiagram.elements;

import org.gaertner.annotations.Visibility;

public class Field extends TypedElement {

	private String name;
	private Class enclosingElement;
	private Visibility visibility;
	
	public Field(String type, String name, Visibility visibility) {
		this(type, name, visibility, null);
	}
	
	public Field(String type, String name, Visibility visibility, String referenceType) {
		super(type, referenceType);
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
