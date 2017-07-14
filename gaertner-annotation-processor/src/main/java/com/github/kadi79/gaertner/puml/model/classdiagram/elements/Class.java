package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.kadi79.gaertner.annotations.Visibility;

public class Class implements Comparable<Class> {

	public static List<Visibility> DEFAULT_FIELDS_TO_DISPLAY = Collections.unmodifiableList( //
			Arrays.asList( //
					Visibility.PUBLIC, //
					Visibility.PACKAGE_PRIVATE, //
					Visibility.PROTECTED, //
					Visibility.PRIVATE //
			));

	public static List<Visibility> DEFAULT_METHODS_TO_DISPLAY = Collections.unmodifiableList( //
			Arrays.asList( //
					Visibility.PUBLIC, //
					Visibility.PACKAGE_PRIVATE //
			));

	private String fqdn;

	private String extendsType;

	private List<Field> allFields = new ArrayList<>();

	private List<Field> displayFields = null;

	private List<Method> allMethods = new ArrayList<>();

	private List<Method> displayMethods = null;

	private List<Visibility> fieldsToDisplay;

	private List<Visibility> methodsToDisplay;

	public Class(String fqdn, List<Visibility> fieldsToDisplay,  List<Visibility> methodsToDisplay) {
		super();
		this.fqdn = fqdn;
		this.fieldsToDisplay = fieldsToDisplay;
		this.methodsToDisplay = methodsToDisplay;
	}

	public String getFqdn() {
		return fqdn;
	}

	public void addField(Field field) {
		allFields.add(field);
		field.setEnclosingElement(this);
		displayFields = null;
	}

	public List<Field> getFields() {
		return allFields;
	}

	public List<Field> getDisplayFields() {
		if (displayFields == null) {
			displayFields = allFields.stream().filter(f->fieldsToDisplay.contains(f.getVisibility())).collect(Collectors.toList());
		}
		return displayFields;
	}

	public void addMethod(Method method) {
		allMethods.add(method);
		method.setEnclosingElement(this);
		displayMethods = null;
	}

	public List<Method> getMethods() {
		return allMethods;
	}

	public List<Method> getDisplayMethods() {
		if (displayMethods == null) {
			displayMethods = allMethods.stream().filter(m->methodsToDisplay.contains(m.getVisibility())).collect(Collectors.toList());
		}
		return displayMethods;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fqdn == null) ? 0 : fqdn.hashCode());
		return result;
	}

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

	@Override
	public int compareTo(Class o) {
		return fqdn.compareTo(o.fqdn);
	}

	public String getExtendsType() {
		return extendsType;
	}

	public void setExtendsType(String extendsType) {
		this.extendsType = extendsType;
	}
}
