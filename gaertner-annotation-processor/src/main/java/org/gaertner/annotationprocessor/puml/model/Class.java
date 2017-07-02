package org.gaertner.annotationprocessor.puml.model;

import java.util.ArrayList;
import java.util.List;

public class Class implements Comparable<Class> {

	private String fqdn;

	private List<Field> fields = new ArrayList<>();
	
	public Class(String fqdn) {
		super();
		this.fqdn = fqdn;
	}

	public String getFqdn() {
		return fqdn;
	}
	
	public void addField(Field field) {
		fields.add(field);
		field.setEnclosingElement(this);
	}
	
	public List<Field> getFields() {
		return fields;
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
}
