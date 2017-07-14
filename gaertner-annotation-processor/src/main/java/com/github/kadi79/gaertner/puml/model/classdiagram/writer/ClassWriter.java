package com.github.kadi79.gaertner.puml.model.classdiagram.writer;

import java.io.PrintWriter;
import java.io.Writer;

import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Field;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Method;

public class ClassWriter {
	private PrintWriter out;

	public ClassWriter(Writer out) {
		this.out = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
	}

	public void write(Class clazz) {
		out.print("class ");
		out.print(clazz.getFqdn());
		out.println(" {");
		for (Field field : clazz.getDisplayFields()) {
			out.println("\t" + field.getVisibility().getPresentation() + field.getSimpleType() + " " + field.getName());
		}
		for (Method method : clazz.getDisplayMethods()) {
			out.println("\t" + method.getVisibility().getPresentation() + method.getSimpleType() + " " + method.getName() + "()");
		}
		out.println("}");
	}
}