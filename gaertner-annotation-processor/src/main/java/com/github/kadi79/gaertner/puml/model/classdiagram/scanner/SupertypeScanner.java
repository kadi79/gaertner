package com.github.kadi79.gaertner.puml.model.classdiagram.scanner;

import java.util.Arrays;
import java.util.List;

import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.RelationType;

/**
 * <p>SupertypeScanner class.</p>
 *
 * @since 0.0.1
 */
public class SupertypeScanner extends RelationScanner {
	/**
	 * <p>Constructor for SupertypeScanner.</p>
	 */
	public SupertypeScanner() {
		super(RelationType.EXTENSION, true);
	}

	/** {@inheritDoc} */
	@Override
	protected List<String> extractRelationTargetKeys(Class clazz) {
		return Arrays.asList(clazz.getExtendsType());
	}
}
