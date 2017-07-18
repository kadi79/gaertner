package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

/**
 * <p>Relation class.</p>
 *
 * @since 0.0.1
 */
public class Relation implements Comparable<Relation> {
	private RelationType relationType;
	private Class from;
	private Class to;
	
	/**
	 * <p>Constructor for Relation.</p>
	 *
	 * @param relationType a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.RelationType} object.
	 * @param from a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class} object.
	 * @param to a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class} object.
	 */
	public Relation(RelationType relationType, Class from, Class to) {
		super();
		this.relationType = relationType;
		this.from = from;
		this.to = to;
	}

	/**
	 * <p>Getter for the field <code>relationType</code>.</p>
	 *
	 * @return a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.RelationType} object.
	 */
	public RelationType getRelationType() {
		return relationType;
	}

	/**
	 * <p>Getter for the field <code>from</code>.</p>
	 *
	 * @return a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class} object.
	 */
	public Class getFrom() {
		return from;
	}

	/**
	 * <p>Getter for the field <code>to</code>.</p>
	 *
	 * @return a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class} object.
	 */
	public Class getTo() {
		return to;
	}

	/** {@inheritDoc} */
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
