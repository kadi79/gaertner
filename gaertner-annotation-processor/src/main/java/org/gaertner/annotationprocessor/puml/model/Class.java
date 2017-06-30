package org.gaertner.annotationprocessor.puml.model;

public class Class {

	private String fqdn;

	public Class(String fqdn) {
		super();
		this.fqdn = fqdn;
	}

	public String getFqdn() {
		return fqdn;
	}
}
