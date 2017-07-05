package org.gaertner.annotationprocessor.puml.model.classdiagram;

import java.io.StringWriter;

import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Field;
import org.gaertner.annotations.Visibility;
import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Class;
import org.junit.Assert;
import org.junit.Test;

public class ClassDiagramTest {

	private static final String nl = System.lineSeparator();
	@Test
	public void singleEmptyClass() throws Exception {
		ClassDiagram classDiagram = new ClassDiagram();
		classDiagram.addClass(new Class("org.gaertner.Class1", Class.DEFAULT_FIELDS_TO_DISPLAY, Class.DEFAULT_METHODS_TO_DISPLAY));
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
		classDiagram.addClass(new Class("org.gaertner.Class1", Class.DEFAULT_FIELDS_TO_DISPLAY, Class.DEFAULT_METHODS_TO_DISPLAY));
		classDiagram.addClass(new Class("org.gaertner.Class2", Class.DEFAULT_FIELDS_TO_DISPLAY, Class.DEFAULT_METHODS_TO_DISPLAY));
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
	
	@Test
	public void composition() throws Exception {
		ClassDiagram classDiagram = new ClassDiagram();
		Class clazz1 = new Class("org.gaertner.Class1", Class.DEFAULT_FIELDS_TO_DISPLAY, Class.DEFAULT_METHODS_TO_DISPLAY);
		classDiagram.addClass(clazz1);
		Class clazz2 = new Class("org.gaertner.Class2", Class.DEFAULT_FIELDS_TO_DISPLAY, Class.DEFAULT_METHODS_TO_DISPLAY);
		clazz2.addField(new Field(clazz1.getFqdn(), "ref", Visibility.PRIVATE));
		classDiagram.addClass(clazz2);
		StringWriter stringWriter = new StringWriter();
		classDiagram.write(stringWriter);

		Assert.assertEquals(//
				"@startuml" + nl  //
				+ "org.gaertner.Class2*--org.gaertner.Class1" + nl //
				+ nl //
				+ "class org.gaertner.Class1 {" + nl  //
				+ "}" + nl  //
				+ nl  //
				+ "class org.gaertner.Class2 {" + nl  //
				+ "\t-Class1 ref" + nl //
				+ "}" + nl  //
				+ "@enduml" + nl , //
				stringWriter.toString());
	}

	@Test
	public void compositionReverseOrder() throws Exception {
		ClassDiagram classDiagram = new ClassDiagram();
		Class clazz1 = new Class("org.gaertner.Class1", Class.DEFAULT_FIELDS_TO_DISPLAY, Class.DEFAULT_METHODS_TO_DISPLAY);
		Class clazz2 = new Class("org.gaertner.Class2", Class.DEFAULT_FIELDS_TO_DISPLAY, Class.DEFAULT_METHODS_TO_DISPLAY);
		clazz2.addField(new Field(clazz1.getFqdn(), "ref", Visibility.PRIVATE));
		classDiagram.addClass(clazz2);
		classDiagram.addClass(clazz1);
		StringWriter stringWriter = new StringWriter();
		classDiagram.write(stringWriter);

		Assert.assertEquals(//
				"@startuml" + nl  //
				+ "org.gaertner.Class2*--org.gaertner.Class1" + nl //
				+ nl //
				+ "class org.gaertner.Class1 {" + nl  //
				+ "}" + nl  //
				+ nl  //
				+ "class org.gaertner.Class2 {" + nl  //
				+ "\t-Class1 ref" + nl //
				+ "}" + nl  //
				+ "@enduml" + nl , //
				stringWriter.toString());
	}

}
