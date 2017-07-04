package org.gaertner.annotationprocessor.puml.model.classdiagram.elements;

public class Relation implements Comparable<Relation> {
	private RelationType relationType;
	private Class from;
	private Class to;
	
	public Relation(RelationType relationType, Class from, Class to) {
		super();
		this.relationType = relationType;
		this.from = from;
		this.to = to;
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public Class getFrom() {
		return from;
	}

	public Class getTo() {
		return to;
	}

	@Override
	public int compareTo(Relation o) {
		int result = from.getFqdn().compareTo(o.getFrom().getFqdn());
		if (result == 0) {
			result = to.getFqdn().compareTo(o.getTo().getFqdn());
		}
		if (result == 0) {
			result = relationType.name().compareTo(o.relationType.name());
		}
		return result;
	}
}