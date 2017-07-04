package org.gaertner.annotationprocessor.puml.model.classdiagram;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Class;
import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Relation;
import org.gaertner.annotationprocessor.puml.model.classdiagram.scanner.CompositionScanner;
import org.gaertner.annotationprocessor.puml.model.classdiagram.scanner.RelationScanner;
import org.gaertner.annotationprocessor.puml.model.classdiagram.scanner.SupertypeScanner;
import org.gaertner.annotationprocessor.puml.model.classdiagram.writer.ClassWriter;
import org.gaertner.annotationprocessor.puml.model.classdiagram.writer.RelationWriter;

public class ClassDiagram {

	private SortedSet<Class> classes = new TreeSet<>();
	private List<RelationScanner> relationScanners = Arrays.asList(new SupertypeScanner(), new CompositionScanner());
	

	public void addClass(Class clazz) {
		classes.add(clazz);
		for (RelationScanner scanner : relationScanners) {
			scanner.scanClass(clazz);
		}
	}

	public void write(Writer out) {
		PrintWriter writer = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
		RelationWriter relationWriter = new RelationWriter(writer);
		ClassWriter classWriter = new ClassWriter(writer);

		writer.println("@startuml");
		for (RelationScanner relationScanner : relationScanners) {
			SortedSet<Relation> relations = relationScanner.getFoundRelations();
			if (relations.isEmpty()) continue;
			for (Relation relation : relations) {
				relationWriter.write(relation);
			}
			writer.println();
		}
		
		boolean first = true;
		for (Class clazz : classes) {
			if (!first)
				writer.println();
			first = false;
			classWriter.write(clazz);
		}
		writer.println("@enduml");
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
