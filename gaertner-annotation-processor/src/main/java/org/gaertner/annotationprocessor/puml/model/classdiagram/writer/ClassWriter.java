package org.gaertner.annotationprocessor.puml.model.classdiagram.writer;

import java.io.PrintWriter;
import java.io.Writer;

import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Class;
import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Field;
import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Method;

public class ClassWriter {
	private PrintWriter out;

	public ClassWriter(Writer out) {
		this.out = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
	}

	public void write(Class clazz) {
		out.print("class ");
		out.print(clazz.getFqdn());
		out.println(" {");
		for (Field field : clazz.getFields()) {
			out.println("\t" + field.getVisibility().getPresentation() + field.getSimpleType() + " " + field.getName());
		}
		for (Method method : clazz.getMethods()) {
			out.println("\t" + method.getVisibility().getPresentation() + method.getSimpleType() + " " + method.getName() + "()");
		}
		out.println("}");
	}
}