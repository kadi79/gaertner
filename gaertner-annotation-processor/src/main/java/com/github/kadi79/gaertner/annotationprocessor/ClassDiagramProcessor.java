package com.github.kadi79.gaertner.annotationprocessor;

import static java.util.Arrays.asList;

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
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import com.github.kadi79.gaertner.annotationprocessor.util.TeeWriter;
import com.github.kadi79.gaertner.annotations.ReferenceType;
import com.github.kadi79.gaertner.annotations.Stereotype;
import com.github.kadi79.gaertner.annotations.Stereotype.HighlightType;
import com.github.kadi79.gaertner.annotations.UmlClassDiagram;
import com.github.kadi79.gaertner.annotations.UmlClassDiagrams;
import com.github.kadi79.gaertner.annotations.Visibility;
import com.github.kadi79.gaertner.puml.model.Color;
import com.github.kadi79.gaertner.puml.model.DiagramFactory;
import com.github.kadi79.gaertner.puml.model.classdiagram.ClassDiagram;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Class;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Field;
import com.github.kadi79.gaertner.puml.model.classdiagram.elements.Method;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

/**
 * <p>ClassDiagramProcessor class.</p>
 * <p>processes all {@link com.github.kadi79.gaertner.annotations.UmlClassDiagram} and {@link com.github.kadi79.gaertner.annotations.UmlClassDiagrams} Annotations. For each Filename a puml File is created containing a collection of all Classes annotated with the same Filename. If possible the puml Files are transformed to svg files.</p>
 *
 * @since 0.0.1
 */
@SupportedAnnotationTypes({"com.github.kadi79.gaertner.annotations.UmlClassDiagram, com.github.kadi79.gaertner.annotations.UmlClassDiagrams"})
public class ClassDiagramProcessor extends AbstractProcessor {

	private Messager messager = null;
	private Map<String, ClassDiagram> classDiagrams = new HashMap<>();
	private Filer filer = null;
	private DiagramFactory diagramFactory;
	
	/**
	 * <p>Constructor for ClassDiagramProcessor.</p>
	 *
	 * @param diagramFactory a {@link com.github.kadi79.gaertner.puml.model.DiagramFactory} object.
	 * @since 0.0.2
	 */
	public ClassDiagramProcessor(DiagramFactory diagramFactory) {
		this.diagramFactory = diagramFactory;
	}
	
	/**
	 * <p>Constructor for ClassDiagramProcessor.</p>
	 *
	 * @since 0.0.2
	 */
	public ClassDiagramProcessor() {
		this.diagramFactory = DiagramFactory.getInstance();
	}

	/** {@inheritDoc} */
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
				FileObject pumlResource = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", filename + ".puml");
				FileObject svgResource = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", filename + ".svg");

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
						throw new IllegalStateException("Error closing Writer", e);
					}
					pumlFileWriter = null;
				}
				if (svgOutputStreams != null) {
					try {
						svgOutputStreams.close();
					} catch (IOException e) {
						throw new IllegalStateException("Error closing Stream", e);
					}
					svgOutputStreams = null;
				}
			}
		}
	}

	private void buildDiagramModels(RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(UmlClassDiagram.class)) {
			UmlClassDiagram annotation = element.getAnnotation(UmlClassDiagram.class);
			if (element instanceof TypeElement) {
				TypeElement typeElement = (TypeElement) element;
				ClassDiagram diagram = getOrCreateClassDiagram(typeElement, annotation);
				diagram.addClass(createClassElement(typeElement, annotation));
			}
		}
		for (Element element : roundEnv.getElementsAnnotatedWith(UmlClassDiagrams.class)) {
			UmlClassDiagram[] annotations = element.getAnnotationsByType(UmlClassDiagram.class);
			for (UmlClassDiagram annotation : annotations) {
				if (element instanceof TypeElement) {
					TypeElement typeElement = (TypeElement) element;
					ClassDiagram diagram = getOrCreateClassDiagram(typeElement, annotation);
					diagram.addClass(createClassElement(typeElement, annotation));
				}
			}
		}
	}

	private Class createClassElement(TypeElement typeElement, UmlClassDiagram annotation) {
		Name qualifiedName = typeElement.getQualifiedName();
		Class clazz = new Class(qualifiedName.toString(), asList(annotation.fields()), asList(annotation.methods()));
		Stereotype stereotype = annotation.stereotype();
		boolean specifiedHighlighting = HighlightType.SPECIFIED.equals(stereotype.highlightType());
		boolean namePresent = isNotEmpty(stereotype.type());
		if (namePresent || specifiedHighlighting) {
			clazz.setStereotype(new com.github.kadi79.gaertner.puml.model.classdiagram.elements.Stereotype(namePresent?stereotype.type():null, specifiedHighlighting?stereotype.highlightChar():null, new Color(stereotype.highlightColor())));
		}
		List<? extends Element> elements = typeElement.getEnclosedElements();
		for (Element element : elements) {
			ElementKind kind = element.getKind();
			switch (kind) {
			case FIELD:
				VariableElement variable = (VariableElement) element;
				ReferenceType referenceType = variable.getAnnotation(ReferenceType.class);
				Field field = new Field(variable.asType().toString(), variable.getSimpleName().toString(), mapToVisibility(variable.getModifiers()), referenceType == null?null:referenceType.value());
				clazz.addField(field);
				break;
			case METHOD:
				ExecutableElement executable = (ExecutableElement) element;
				Method method = new Method(executable.asType().toString(), executable.getSimpleName().toString(), mapToVisibility(executable.getModifiers()));
				clazz.addMethod(method);
				break;

			default:
				break;
			}
		}
		
		TypeMirror superclass = typeElement.getSuperclass();
		if (superclass instanceof DeclaredType) {
			DeclaredType declared = (DeclaredType) superclass;
			TypeElement element = (TypeElement) declared.asElement();
			Name qualifiedSupertypeName = element.getQualifiedName();
			clazz.setExtendsType(qualifiedSupertypeName.toString());
		}
		
		return clazz;
	}

	private boolean isNotEmpty(String type) {
		return type != null && !type.isEmpty();
	}

	private Visibility mapToVisibility(Set<Modifier> modifiers) {
		for (Modifier modifier : modifiers) {
			switch (modifier) {
			case PUBLIC:
				return Visibility.PUBLIC;
			case PROTECTED:
				return Visibility.PROTECTED;
			case PRIVATE:
				return Visibility.PRIVATE;
			default:
				break;
			}
		}
		return Visibility.PACKAGE_PRIVATE;
	}

	private ClassDiagram getOrCreateClassDiagram(TypeElement typeElement, UmlClassDiagram annotation) {
		String filename = annotation.filename();
		ClassDiagram classDiagram = classDiagrams.get(filename);
		if (classDiagram == null) {
			classDiagram = diagramFactory.createClassDiagram(filename);
			classDiagrams.put(filename, classDiagram);
		}
		return classDiagram;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		messager  = processingEnv.getMessager();
		filer  = processingEnv.getFiler();
	}

	/** {@inheritDoc} */
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> annotations = new HashSet<>();
		annotations.add(UmlClassDiagram.class.getCanonicalName());
		annotations.add(UmlClassDiagrams.class.getCanonicalName());
		return annotations ;
	}

	/** {@inheritDoc} */
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
