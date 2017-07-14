package com.github.kadi79.gaertner.puml.model.classdiagram.writer;

import java.io.PrintWriter;
import java.io.Writer;

import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Relation;

public class RelationWriter {
	private PrintWriter out;
	
	public RelationWriter(Writer out) {
		this.out = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
	}
	
	public void write(Relation relation) {
		out.println(String.format("%s%s--%s", relation.getFrom().getFqdn(), relation.getRelationType().getArrowHead(), relation.getTo().getFqdn()));
	}
}