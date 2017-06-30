package org.gaertner.annotationprocessor;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.gaertner.annotationprocessor.puml.model.Class;
import org.gaertner.annotationprocessor.puml.model.ClassDiagram;
import org.gaertner.annotations.UmlClassDiagram;

@SupportedAnnotationTypes({"org.gaertner.annotations.UmlClassDiagram"})
public class ClassDiagramProcessor extends AbstractProcessor {

	private Messager messager = null;
	private Map<String, ClassDiagram> classDiagrams = new HashMap<>();
	private Filer filer = null;

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (roundEnv.processingOver()) return false;
		buildDiagramModels(roundEnv);
		writeDiagrams();
		return true;
	}

	private void writeDiagrams() {
		for (Entry<String, ClassDiagram> entry : classDiagrams.entrySet()) {
			String filename = entry.getKey() + ".puml";
			Writer writer = null;
			try {
				messager.printMessage(Kind.NOTE, "writing Diagram File " + filename);
				FileObject resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "puml", filename);
				messager.printMessage(Kind.NOTE, "writing File " + resource.toUri());
				writer = resource.openWriter();
				entry.getValue().write(writer);
				writer.flush();
			} catch (IOException e) {
				messager.printMessage(Kind.ERROR, "Error creating " + filename + ":\n" + e.getMessage());
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					writer = null;
				}
			}
		}
	}

	private void buildDiagramModels(RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(UmlClassDiagram.class)) {
			if (element instanceof TypeElement) {
				TypeElement typeElement = (TypeElement) element;
				ClassDiagram diagram = getOrCreateClassDiagram(typeElement);
				diagram.addClass(createClassElement(typeElement));
			}
		}
	}

	private Class createClassElement(TypeElement typeElement) {
		Name qualifiedName = typeElement.getQualifiedName();
		Class clazz = new Class(qualifiedName.toString());
		return clazz;
	}

	private ClassDiagram getOrCreateClassDiagram(TypeElement typeElement) {
		UmlClassDiagram annotation = typeElement.getAnnotation(UmlClassDiagram.class);
		String filename = annotation.filename();
		ClassDiagram classDiagram = classDiagrams.get(filename);
		if (classDiagram == null) {
			classDiagram = new ClassDiagram();
			classDiagrams.put(filename, classDiagram);
		}
		return classDiagram;
	}

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		messager  = processingEnv.getMessager();
		filer  = processingEnv.getFiler();
	}
	
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> annotations = new HashSet<>();
		annotations.add(UmlClassDiagram.class.getCanonicalName());
		return annotations ;
	}
	
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
