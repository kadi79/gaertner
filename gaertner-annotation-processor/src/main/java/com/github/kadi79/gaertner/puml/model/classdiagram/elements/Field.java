package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

import com.github.kadi79.gaertner.annotations.Visibility;

/**
 * <p>Field class.</p>
 *
 * @since 0.0.1
 */
public class Field extends TypedElement {

	private String name;
	private Class enclosingElement;
	private Visibility visibility;
	
	/**
	 * <p>Constructor for Field.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @param visibility a {@link com.github.kadi79.gaertner.annotations.Visibility} object.
	 */
	public Field(String type, String name, Visibility visibility) {
		this(type, name, visibility, null);
	}
	
	/**
	 * <p>Constructor for Field.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @param visibility a {@link com.github.kadi79.gaertner.annotations.Visibility} object.
	 * @param referenceType a {@link java.lang.String} object.
	 */
	public Field(String type, String name, Visibility visibility, String referenceType) {
		super(type, referenceType);
		this.name = name;
		this.visibility = visibility;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * <p>Getter for the field <code>visibility</code>.</p>
	 *
	 * @return a {@link com.github.kadi79.gaertner.annotations.Visibility} object.
	 */
	public Visibility getVisibility() {
		return visibility;
	}
	
	/**
	 * <p>Getter for the field <code>enclosingElement</code>.</p>
	 *
	 * @return a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class} object.
	 */
	public Class getEnclosingElement() {
		return enclosingElement;
	}
	
	void setEnclosingElement(Class enclosingElement) {
		this.enclosingElement = enclosingElement;
	}
}
