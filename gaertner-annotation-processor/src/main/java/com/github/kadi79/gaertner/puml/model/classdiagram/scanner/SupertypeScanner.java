package com.github.kadi79.gaertner.puml.model.classdiagram.scanner;

import java.util.Arrays;
import java.util.List;

import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.RelationType;

public class SupertypeScanner extends RelationScanner {
	public SupertypeScanner() {
		super(RelationType.EXTENSION, true);
	}

	@Override
	protected List<String> extractRelationTargetKeys(Class clazz) {
		return Arrays.asList(clazz.getExtendsType());
	}
}