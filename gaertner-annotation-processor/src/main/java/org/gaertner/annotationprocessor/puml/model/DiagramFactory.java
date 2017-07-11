package org.gaertner.annotationprocessor.puml.model;

import org.gaertner.annotationprocessor.puml.model.classdiagram.ClassDiagram;

public class DiagramFactory {

	private static final DiagramFactory instance = new DiagramFactory();
	
	protected DiagramFactory() {
	}

	public static DiagramFactory getInstance() {
		return instance;
	}
	
	public ClassDiagram createClassDiagram() {
		return new ClassDiagram();
	}
}
