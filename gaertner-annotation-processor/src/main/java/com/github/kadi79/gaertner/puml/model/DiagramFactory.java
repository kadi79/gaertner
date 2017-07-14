package com.github.kadi79.gaertner.puml.model;

import com.github.kadi79.gaertner.puml.model.classdiagram.ClassDiagram;

public class DiagramFactory {

	private static final DiagramFactory instance = new DiagramFactory();
	
	protected DiagramFactory() {
	}

	public static DiagramFactory getInstance() {
		return instance;
	}
	
	public ClassDiagram createClassDiagram(String diagramName) {
		return new ClassDiagram(diagramName);
	}
}
