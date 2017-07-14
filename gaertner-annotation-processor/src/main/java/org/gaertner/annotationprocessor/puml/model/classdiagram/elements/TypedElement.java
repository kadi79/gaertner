package org.gaertner.annotationprocessor.puml.model.classdiagram.elements;

public class TypedElement {

	private String type;
	private String simpleType = null;
	private String referenceType;
	private String simpleReferenceType;

	public TypedElement(String type) {
		this(type, null);
	}

	public TypedElement(String type, String referenceType) {
		super();
		this.type = type;
		this.referenceType = referenceType;
	}

	public String getType() {
		return type;
	}

	public String getSimpleType() {
		if (simpleType == null)
			simpleType = shortenTypeName(type);
		return simpleType;
	}

	private String shortenTypeName(String string) {
		return string.replaceAll("\\(.*\\)", "").replaceAll("<[^<>]*\\.([^\\.<>]+)>", "<$1>").replaceAll("^.*\\.", "");
	}

	public String getReferenceType() {
		if (referenceType == null)
			return getType();
		return referenceType;
	}

	public String getSimpleReferenceType() {
		if (simpleReferenceType == null)
			simpleReferenceType = shortenTypeName(getReferenceType());
		return simpleReferenceType;
	}
}
