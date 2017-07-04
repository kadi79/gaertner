package org.gaertner.annotationprocessor.puml.model.classdiagram.elements;

public class TypedElement {

	private String type;
	private String simpleType = null;

	public TypedElement(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public String getSimpleType() {
		if (simpleType == null) simpleType = type.substring(type.lastIndexOf('.') + 1);
 		return simpleType;
	}

}
