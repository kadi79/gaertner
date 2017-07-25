package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.kadi79.gaertner.annotations.Visibility;

/**
 * <p>Model representation of a class of the {@link com.github.kadi79.gaertner.puml.model.classdiagram.ClassDiagram}</p>
 *
 * @since 0.0.1
 */
public class Class implements Comparable<Class> {

	/** Constant <code>DEFAULT_FIELDS_TO_DISPLAY</code> */
	public static List<Visibility> DEFAULT_FIELDS_TO_DISPLAY = Collections.unmodifiableList( //
			Arrays.asList( //
					Visibility.PUBLIC, //
					Visibility.PACKAGE_PRIVATE, //
					Visibility.PROTECTED, //
					Visibility.PRIVATE //
			));

	/** Constant <code>DEFAULT_METHODS_TO_DISPLAY</code> */
	public static List<Visibility> DEFAULT_METHODS_TO_DISPLAY = Collections.unmodifiableList( //
			Arrays.asList( //
					Visibility.PUBLIC, //
					Visibility.PACKAGE_PRIVATE //
			));

	private String fqdn;
	
	private Stereotype stereotype;
	
	private String extendsType;

	private List<Field> allFields = new ArrayList<>();

	private List<Field> displayFields = null;

	private List<Method> allMethods = new ArrayList<>();

	private List<Method> displayMethods = null;

	private List<Visibility> fieldsToDisplay;

	private List<Visibility> methodsToDisplay;

	/**
	 * <p>Constructor for Class.</p>
	 *
	 * @param fqdn a {@link java.lang.String} object.
	 * @param fieldsToDisplay a {@link java.util.List} object.
	 * @param methodsToDisplay a {@link java.util.List} object.
	 */
	public Class(String fqdn, List<Visibility> fieldsToDisplay,  List<Visibility> methodsToDisplay) {
		super();
		this.fqdn = fqdn;
		this.fieldsToDisplay = fieldsToDisplay;
		this.methodsToDisplay = methodsToDisplay;
	}

	/**
	 * <p>Getter for the field <code>fqdn</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getFqdn() {
		return fqdn;
	}

	/**
	 * <p>addField.</p>
	 *
	 * @param field a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Field} object.
	 */
	public void addField(Field field) {
		allFields.add(field);
		field.setEnclosingElement(this);
		displayFields = null;
	}

	/**
	 * <p>getFields.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Field> getFields() {
		return allFields;
	}

	/**
	 * <p>Getter for the field <code>displayFields</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Field> getDisplayFields() {
		if (displayFields == null) {
			displayFields = allFields.stream().filter(f->fieldsToDisplay.contains(f.getVisibility())).collect(Collectors.toList());
		}
		return displayFields;
	}

	/**
	 * <p>addMethod.</p>
	 *
	 * @param method a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Method} object.
	 */
	public void addMethod(Method method) {
		allMethods.add(method);
		method.setEnclosingElement(this);
		displayMethods = null;
	}

	/**
	 * <p>getMethods.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Method> getMethods() {
		return allMethods;
	}

	/**
	 * <p>Getter for the field <code>displayMethods</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Method> getDisplayMethods() {
		if (displayMethods == null) {
			displayMethods = allMethods.stream().filter(m->methodsToDisplay.contains(m.getVisibility())).collect(Collectors.toList());
		}
		return displayMethods;
	}
	
	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fqdn == null) ? 0 : fqdn.hashCode());
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Class other = (Class) obj;
		if (fqdn == null) {
			if (other.fqdn != null)
				return false;
		} else if (!fqdn.equals(other.fqdn))
			return false;
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public int compareTo(Class o) {
		return fqdn.compareTo(o.fqdn);
	}

	/**
	 * <p>Getter for the field <code>extendsType</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getExtendsType() {
		return extendsType;
	}

	/**
	 * <p>Setter for the field <code>extendsType</code>.</p>
	 *
	 * @param extendsType a {@link java.lang.String} object.
	 */
	public void setExtendsType(String extendsType) {
		this.extendsType = extendsType;
	}

	public Stereotype getStereotype() {
		return stereotype;
	}

	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}
}
