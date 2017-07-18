package com.github.kadi79.gaertner.puml.model;

import com.github.kadi79.gaertner.puml.model.classdiagram.ClassDiagram;

/**
 * <p>DiagramFactory class.</p>
 *
 * @since 0.0.1
 */
public class DiagramFactory {

	private static final DiagramFactory instance = new DiagramFactory();
	
	/**
	 * <p>Constructor for DiagramFactory.</p>
	 */
	protected DiagramFactory() {
	}

	/**
	 * <p>Getter for the field <code>instance</code>.</p>
	 *
	 * @return a {@link com.github.kadi79.gaertner.puml.model.DiagramFactory} object.
	 */
	public static DiagramFactory getInstance() {
		return instance;
	}
	
	/**
	 * <p>createClassDiagram.</p>
	 *
	 * @param diagramName a {@link java.lang.String} object.
	 * @return a {@link com.github.kadi79.gaertner.puml.model.classdiagram.ClassDiagram} object.
	 */
	public ClassDiagram createClassDiagram(String diagramName) {
		return new ClassDiagram(diagramName);
	}
}
