package org.gaertner.annotationprocessor.puml.model.classdiagram.scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Class;
import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Relation;
import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.RelationType;

public abstract class RelationScanner {
	
	private RelationType relationType;
	private boolean reversed = false;
	private Map<String, List<Class>> missingRelationPartners = new HashMap<>();
	private Map<String, Class> scanned = new HashMap<>();
	private SortedSet<Relation> foundRelations = new TreeSet<>();
	
	public RelationScanner(RelationType relationType) {
		this(relationType, false);
	}
	
	public RelationScanner(RelationType relationType, boolean reversed) {
		this.relationType = relationType;
		this.reversed = reversed;
	}
	
	public SortedSet<Relation> getFoundRelations() {
		return foundRelations;
	}
	
	public void scanClass(Class current) {
		scanned.put(current.getFqdn(), current);
		
		for (String targetKey : extractRelationTargetKeys(current)) {
			if (scanned.get(targetKey) != null) {
				createRelation(current, scanned.get(targetKey));
			} else {
				addToMissingRelationPartners(targetKey, current);
			}
		}

		List<Class> list = missingRelationPartners.get(current.getFqdn());
		if (list != null) {
			for (Class source : list) {
				createRelation(source, current);
			}
		}
		missingRelationPartners.remove(current.getFqdn());
	}

	private void addToMissingRelationPartners(String key, Class clazz) {
		List<Class> list = missingRelationPartners.get(key);
		if (list == null) {
			list = new ArrayList<>();
			missingRelationPartners.put(key, list);
		}
		list.add(clazz);
	}

	private void createRelation(Class source, Class target) {
		if (!reversed) {
			foundRelations.add(new Relation(relationType, source, target));
		} else {
			foundRelations.add(new Relation(relationType, target, source));
		}
	}

	protected abstract List<String> extractRelationTargetKeys(Class current);
	
}