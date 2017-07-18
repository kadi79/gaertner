package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

/**
 * <p>TypedElement class.</p>
 *
 * @since 0.0.1
 */
public class TypedElement {

	private String type;
	private String simpleType = null;
	private String referenceType;
	private String simpleReferenceType;

	/**
	 * <p>Constructor for TypedElement.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	public TypedElement(String type) {
		this(type, null);
	}

	/**
	 * <p>Constructor for TypedElement.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param referenceType a {@link java.lang.String} object.
	 */
	public TypedElement(String type, String referenceType) {
		super();
		this.type = type;
		this.referenceType = referenceType;
	}

	/**
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getType() {
		return type;
	}

	/**
	 * <p>Getter for the field <code>simpleType</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSimpleType() {
		if (simpleType == null)
			simpleType = shortenTypeName(type);
		return simpleType;
	}

	private String shortenTypeName(String string) {
		return string.replaceAll("\\(.*\\)", "").replaceAll("<[^<>]*\\.([^\\.<>]+)>", "<$1>").replaceAll("^.*\\.", "");
	}

	/**
	 * <p>Getter for the field <code>referenceType</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getReferenceType() {
		if (referenceType == null)
			return getType();
		return referenceType;
	}

	/**
	 * <p>Getter for the field <code>simpleReferenceType</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSimpleReferenceType() {
		if (simpleReferenceType == null)
			simpleReferenceType = shortenTypeName(getReferenceType());
		return simpleReferenceType;
	}
}
