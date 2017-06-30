package org.gaertner.annotationprocessor.puml.model;

import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

public class ClassDiagramTest {

	@Test
	public void singleEmptyClass() throws Exception {
		ClassDiagram classDiagram = new ClassDiagram();
		classDiagram.addClass(new Class("org.gaertner.Class1"));
		StringWriter stringWriter = new StringWriter();
		classDiagram.write(stringWriter);
		
		Assert.assertEquals(//
				"@startuml\n" //
				+ "class org.gaertner.Class1 {\n" //
				+ "}\n" //
				+ "@enduml", //
				stringWriter.toString());
	}
	
	public void twoEmptyClasses() throws Exception {
		ClassDiagram classDiagram = new ClassDiagram();
		classDiagram.addClass(new Class("org.gaertner.Class1"));
		classDiagram.addClass(new Class("org.gaertner.Class2"));
		StringWriter stringWriter = new StringWriter();
		classDiagram.write(stringWriter);
		
		Assert.assertEquals(//
				"@startuml\n" //
				+ "class org.gaertner.Class1 {\n" //
				+ "}\n" //
				+ "\n" //
				+ "class org.gaertner.Class2 {\n" //
				+ "}\n" //
				+ "@enduml", //
				stringWriter.toString());
	}
	
}
