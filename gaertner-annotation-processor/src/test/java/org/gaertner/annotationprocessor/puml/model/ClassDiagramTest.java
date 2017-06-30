package org.gaertner.annotationprocessor.puml.model;

import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

public class ClassDiagramTest {

	private static final String nl = System.lineSeparator();
	@Test
	public void singleEmptyClass() throws Exception {
		ClassDiagram classDiagram = new ClassDiagram();
		classDiagram.addClass(new Class("org.gaertner.Class1"));
		StringWriter stringWriter = new StringWriter();
		classDiagram.write(stringWriter);

		Assert.assertEquals(//
				"@startuml" + nl //
				+ "class org.gaertner.Class1 {" + nl  //
				+ "}"  + nl //
				+ "@enduml"  + nl, //
				stringWriter.toString());
	}

	@Test
	public void twoEmptyClasses() throws Exception {
		ClassDiagram classDiagram = new ClassDiagram();
		classDiagram.addClass(new Class("org.gaertner.Class1"));
		classDiagram.addClass(new Class("org.gaertner.Class2"));
		StringWriter stringWriter = new StringWriter();
		classDiagram.write(stringWriter);

		Assert.assertEquals(//
				"@startuml" + nl  //
				+ "class org.gaertner.Class1 {" + nl  //
				+ "}" + nl  //
				+ nl  //
				+ "class org.gaertner.Class2 {" + nl  //
				+ "}" + nl  //
				+ "@enduml" + nl , //
				stringWriter.toString());
	}

}
