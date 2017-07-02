package org.gaertner.annotationprocessor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.gaertner.annotationprocessor.puml.model.Class;
import org.gaertner.annotationprocessor.puml.model.ClassDiagram;
import org.gaertner.annotationprocessor.puml.model.Field;
import org.gaertner.annotationprocessor.util.TeeWriter;
import org.gaertner.annotations.UmlClassDiagram;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

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
			String filename = entry.getKey();
			Writer pumlFileWriter = null;
			OutputStream svgOutputStreams = null;
			try {
				messager.printMessage(Kind.NOTE, "writing Diagram File " + filename);
				FileObject pumlResource = filer.createResource(StandardLocation.CLASS_OUTPUT, "puml", filename + ".puml");
				FileObject svgResource = filer.createResource(StandardLocation.CLASS_OUTPUT, "puml", filename + ".svg");

				StringWriter stringWriter = new StringWriter();
				pumlFileWriter = new TeeWriter(stringWriter, pumlResource.openWriter());

				entry.getValue().write(pumlFileWriter);
				pumlFileWriter.flush();

				SourceStringReader reader = new SourceStringReader(stringWriter.toString());
				svgOutputStreams = svgResource.openOutputStream();
				reader.outputImage(svgOutputStreams, new FileFormatOption(FileFormat.SVG));

			} catch (IOException e) {
				messager.printMessage(Kind.ERROR, "Error creating " + filename + ":\n" + e.getMessage());
			} finally {
				if (pumlFileWriter != null) {
					try {
						pumlFileWriter.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pumlFileWriter = null;
				}
				if (svgOutputStreams != null) {
					try {
						svgOutputStreams.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					svgOutputStreams = null;
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
		List<? extends Element> elements = typeElement.getEnclosedElements();
		for (Element element : elements) {
			if (element instanceof VariableElement) {
				VariableElement variable = (VariableElement) element;
				Field field = new Field(variable.asType().toString(), variable.getSimpleName().toString());
				clazz.addField(field);
			}
		}
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
