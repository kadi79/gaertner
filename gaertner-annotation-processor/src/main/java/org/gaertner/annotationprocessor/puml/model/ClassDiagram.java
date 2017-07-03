package org.gaertner.annotationprocessor.puml.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ClassDiagram {

	private class ClassWriter {
		private PrintWriter out;

		public ClassWriter(Writer out) {
			this.out = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
		}

		public void write(Class clazz) {
			out.print("class ");
			out.print(clazz.getFqdn());
			out.println(" {");
			for (Field field : clazz.getFields()) {
				out.println("\t" + field.getType() + " " + field.getName());
			}
			out.println("}");
		}
	}
	
	private class RelationWriter {
		private PrintWriter out;
		
		public RelationWriter(Writer out) {
			this.out = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
		}
		
		public void write(Relation relation) {
			out.println(String.format("%s%s--%s", relation.getFrom().getFqdn(), relation.getRelationType().getArrowHead(), relation.getTo().getFqdn()));
		}
	}

	private enum RelationType {
		EXTENSION("<|"), AGGREGATION("o"), COMPOSITION("*");
		
		private String arrowHead;
		
		private RelationType(String arrowHead) {
			this.arrowHead = arrowHead;
		}
		
		public String getArrowHead() {
			return arrowHead;
		}
	}

	private class Relation implements Comparable<Relation> {
		private RelationType relationType;
		private Class from;
		private Class to;
		
		public Relation(RelationType relationType, Class from, Class to) {
			super();
			this.relationType = relationType;
			this.from = from;
			this.to = to;
		}

		public RelationType getRelationType() {
			return relationType;
		}

		public Class getFrom() {
			return from;
		}

		public Class getTo() {
			return to;
		}

		@Override
		public int compareTo(Relation o) {
			int result = from.getFqdn().compareTo(o.getFrom().getFqdn());
			if (result == 0) {
				result = to.getFqdn().compareTo(o.getTo().getFqdn());
			}
			if (result == 0) {
				result = relationType.name().compareTo(o.relationType.name());
			}
			return result;
		}
	}
	
	
	private abstract class RelationScanner {
		
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

	private class CompositionScanner extends RelationScanner {
		public CompositionScanner() {
			super(RelationType.COMPOSITION);
		}

		@Override
		protected List<String> extractRelationTargetKeys(Class clazz) {
			return clazz.getFields().stream().map(Field::getType).collect(Collectors.toList());
		}
	}

	private class SupertypeScanner extends RelationScanner {
		public SupertypeScanner() {
			super(RelationType.EXTENSION, true);
		}

		@Override
		protected List<String> extractRelationTargetKeys(Class clazz) {
			return Arrays.asList(clazz.getExtendsType());
		}
	}

	private SortedSet<Class> classes = new TreeSet<>();
	private List<RelationScanner> relationScanners = Arrays.asList(new SupertypeScanner(), new CompositionScanner());
	

	public void addClass(Class clazz) {
		classes.add(clazz);
		for (RelationScanner scanner : relationScanners) {
			scanner.scanClass(clazz);
		}
	}

	public void write(Writer out) {
		PrintWriter writer = (out instanceof PrintWriter) ? (PrintWriter) out : new PrintWriter(out);
		RelationWriter relationWriter = new RelationWriter(writer);
		ClassWriter classWriter = new ClassWriter(writer);

		writer.println("@startuml");
		for (RelationScanner relationScanner : relationScanners) {
			SortedSet<Relation> relations = relationScanner.getFoundRelations();
			if (relations.isEmpty()) continue;
			for (Relation relation : relations) {
				relationWriter.write(relation);
			}
			writer.println();
		}
		
		boolean first = true;
		for (Class clazz : classes) {
			if (!first)
				writer.println();
			first = false;
			classWriter.write(clazz);
		}
		writer.println("@enduml");
		writer.flush();
	}

	public String toString() {
		StringWriter out = new StringWriter();
		write(out);
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}
}
