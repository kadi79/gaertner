package org.gaertner.annotationprocessor.puml.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ClassDiagram {

	private class ClassWriter {
		private PrintWriter out;
		
		public ClassWriter(Writer out) {
			this.out = (out instanceof PrintWriter)?(PrintWriter)out:new PrintWriter(out);
		}
		public void write(Class clazz) {
			out.print("class ");
			out.print(clazz.getFqdn());
			out.println(" {");
			out.println("}");
		}
	}
	
	private List<Class> classes = new ArrayList<>();

	public void addClass(Class clazz) {
		classes.add(clazz);
	}
	
	public void write(Writer out) {
		PrintWriter writer = (out instanceof PrintWriter)?(PrintWriter)out:new PrintWriter(out);
		ClassWriter classWriter = new ClassWriter(writer);
		
		writer.println("@startuml");
		boolean first = true;
		for (Class clazz : classes) {
			if (!first ) writer.println();
			first = false;
			classWriter.write(clazz);
		}
		writer.print("@enduml");
		writer.flush();
	}
	
	public String toString() {
		StringWriter out = new StringWriter();
		write(out);
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}
}
