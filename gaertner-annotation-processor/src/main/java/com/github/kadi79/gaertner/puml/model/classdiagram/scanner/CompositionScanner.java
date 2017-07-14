package com.github.kadi79.gaertner.puml.model.classdiagram.scanner;

import java.util.List;
import java.util.stream.Collectors;

import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Field;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.RelationType;

public class CompositionScanner extends RelationScanner {
	public CompositionScanner() {
		super(RelationType.COMPOSITION);
	}

	@Override
	protected List<String> extractRelationTargetKeys(Class clazz) {
		return clazz.getFields().stream().map(Field::getReferenceType).collect(Collectors.toList());
	}
}