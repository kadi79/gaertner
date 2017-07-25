package com.github.kadi79.gaertner.puml.model.classdiagram.scanner;

import java.util.List;
import java.util.stream.Collectors;

import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Field;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.RelationType;

/**
 * <p>CompositionScanner class.</p>
 *
 * @since 0.0.1
 */
public class CompositionScanner extends RelationScanner {
	/**
	 * <p>Constructor for CompositionScanner.</p>
	 *
	 * @since 0.0.2
	 */
	public CompositionScanner() {
		super(RelationType.COMPOSITION);
	}

	/** {@inheritDoc} */
	@Override
	protected List<String> extractRelationTargetKeys(Class clazz) {
		return clazz.getFields().stream().map(Field::getReferenceType).collect(Collectors.toList());
	}
}
