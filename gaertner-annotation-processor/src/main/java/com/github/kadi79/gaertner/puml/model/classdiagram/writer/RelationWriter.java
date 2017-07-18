package com.github.kadi79.gaertner.puml.model.classdiagram.writer;

import java.io.PrintWriter;
import java.io.Writer;

import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Relation;

/**
 * <p>RelationWriter class.</p>
 *
 * @since 0.0.1
 */
public class RelationWriter {
	private PrintWriter out;
	
	/**
	 * <p>Constructor for RelationWriter.</p>
	 *
	 * @param out a {@link java.io.Writer} object.
	 */
	public RelationWriter(Writer out) {
		this.out = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
	}
	
	/**
	 * <p>write.</p>
	 *
	 * @param relation a {@link com.github.kadi79.gaertner.puml.model.classdiagram.elements.Relation} object.
	 */
	public void write(Relation relation) {
		out.println(String.format("%s%s--%s", relation.getFrom().getFqdn(), relation.getRelationType().getArrowHead(), relation.getTo().getFqdn()));
	}
}
