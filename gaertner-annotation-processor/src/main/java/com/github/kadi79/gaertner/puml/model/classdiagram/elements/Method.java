package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

import com.github.kadi79.gaertner.annotations.Visibility;

public class Method extends TypedElement {

	private String name;
	private Visibility visibility;
	private Class enclosingElement;

	public Method(String type, String name, Visibility visibility) {
		super(type);
		this.name = name;
		this.visibility = visibility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public Class getEnclosingElement() {
		return enclosingElement;
	}

	public void setEnclosingElement(Class enclosingElement) {
		this.enclosingElement = enclosingElement;
	}
}
