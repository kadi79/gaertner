package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

import com.github.kadi79.gaertner.annotations.Visibility;

/**
 * <p>Method class.</p>
 *
 * @since 0.0.1
 */
public class Method extends TypedElement {

	private String name;
	private Visibility visibility;
	private Class enclosingElement;

	/**
	 * <p>Constructor for Method.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @param visibility a {@link com.github.kadi79.gaertner.annotations.Visibility} object.
	 */
	public Method(String type, String name, Visibility visibility) {
		super(type);
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
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * <p>Setter for the field <code>visibility</code>.</p>
	 *
	 * @param visibility a {@link com.github.kadi79.gaertner.annotations.Visibility} object.
	 */
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	/**
	 * <p>Getter for the field <code>enclosingElement</code>.</p>
	 *
	 * @return a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class} object.
	 */
	public Class getEnclosingElement() {
		return enclosingElement;
	}

	/**
	 * <p>Setter for the field <code>enclosingElement</code>.</p>
	 *
	 * @param enclosingElement a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class} object.
	 */
	public void setEnclosingElement(Class enclosingElement) {
		this.enclosingElement = enclosingElement;
	}
}
