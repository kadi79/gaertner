package org.gaertner.annotationprocessor.puml.model.classdiagram.scanner;

import java.util.Arrays;
import java.util.List;

import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Class;
import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.RelationType;

public class SupertypeScanner extends RelationScanner {
	public SupertypeScanner() {
		super(RelationType.EXTENSION, true);
	}

	@Override
	protected List<String> extractRelationTargetKeys(Class clazz) {
		return Arrays.asList(clazz.getExtendsType());
	}
}