package org.gaertner.annotationprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.easymock.Capture;
import org.easymock.CaptureType;
import org.easymock.EasyMock;
import org.easymock.MockType;
import org.gaertner.annotationprocessor.puml.model.DiagramFactory;
import org.gaertner.annotationprocessor.puml.model.classdiagram.ClassDiagram;
import org.gaertner.annotationprocessor.puml.model.classdiagram.elements.Class;
import org.hamcrest.Matchers;
import org.hamcrest.io.FileMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClassDiagramProcessorTest {

	private JavaCompiler compiler;
	private StandardJavaFileManager fileManager;
	private Path targetDir;
	private File classTargetDir;
	private File generatedSourcesTargetDir;
	// private Writer consoleWriter;

	@Before
	public void setup() throws IOException {
		compiler = ToolProvider.getSystemJavaCompiler();
		setupFileManager();
		// setupConsoleOutput();
	}

	// private void setupConsoleOutput() throws UnsupportedEncodingException {
	// StringWriter stringWriter = new StringWriter();
	// OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out,
	// "UTF-8");
	// consoleWriter = new TeeWriter(outputStreamWriter, stringWriter);
	// }

	private void setupFileManager() throws IOException {
		targetDir = Files.createTempDirectory("target");

		classTargetDir = new File(targetDir.toFile(), "classes");
		Files.createDirectory(classTargetDir.toPath());

		generatedSourcesTargetDir = new File(targetDir.toFile(), "generatedSources");
		Files.createDirectory(generatedSourcesTargetDir.toPath());

		fileManager = compiler.getStandardFileManager(null, null, null);
		fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(classTargetDir));
		fileManager.setLocation(StandardLocation.SOURCE_OUTPUT, Arrays.asList(generatedSourcesTargetDir));
	}

	@After
	public void teardown() throws IOException {
		Files.walkFileTree(targetDir, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				if (exc != null)
					throw exc;
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	@Test
	public void testMultipleAnnotationsOnOneClass() throws Exception {
		StringSourceJavaFileObject javaSource = new StringSourceJavaFileObject( //
				"org.gaertner.test.EmptyClass", //
				"package org.gaertner.test;\n" //
						+ "import org.gaertner.annotations.UmlClassDiagram;\n" //
						+ "@UmlClassDiagram(filename=\"test_detail\")\n" //
						+ "@UmlClassDiagram(filename=\"test\", fields= {}, methods={})\n" //
						+ "public class EmptyClass {\n" //
						+ "	int g;\n" //
						+ "}" //
		);

		CompilationTask task = CompilationTaskBuilder //
				.compilationTask(compiler) //
				.withFileManager(fileManager) //
				.withJavaFile(javaSource) //
				.build();
		task.setProcessors(Arrays.asList(new ClassDiagramProcessor()));
		task.call();

		for (List<String> pathParts : Arrays.asList(Arrays.asList("org", "gaertner", "test", "EmptyClass.class"))) {
			File file = createPath(classTargetDir, pathParts);
			Assert.assertThat(file, FileMatchers.anExistingFile());
		}
		for (List<String> pathParts : Arrays.asList(Arrays.asList("test.puml"), Arrays.asList("test.svg"),
				Arrays.asList("test_detail.puml"), Arrays.asList("test_detail.svg"))) {
			File file = createPath(generatedSourcesTargetDir, pathParts);
			Assert.assertThat(file.getAbsolutePath(), file, FileMatchers.anExistingFile());
		}
	}

	@Test
	public void testReferenceTypeAnnotation() throws Exception {
		StringSourceJavaFileObject sourceTestClass1 = new StringSourceJavaFileObject( //
				"org.gaertner.test.TestClass1", //
				"package org.gaertner.test;\n" //
						+ "import org.gaertner.annotations.UmlClassDiagram;\n" //
						+ "import org.gaertner.annotations.ReferenceType;\n" //
						+ "import java.util.ArrayList;\n" //
						+ "import java.util.List;\n"
						+ "@UmlClassDiagram(filename=\"test_detail\")\n" //
						+ "public class TestClass1 {\n" //
						+ "@ReferenceType(\"org.gaertner.test.TestClass2\")\n"
						+ "	List<TestClass2> tests = new ArrayList<>();\n" //
						+ "}" //
		);
		StringSourceJavaFileObject sourceTestClass2 = new StringSourceJavaFileObject( //
				"org.gaertner.test.TestClass2", //
				"package org.gaertner.test;\n" //
						+ "import org.gaertner.annotations.UmlClassDiagram;\n" //
						+ "@UmlClassDiagram(filename=\"test_detail\")\n" //
						+ "public class TestClass2 {\n" //
						+ "	int g;\n" //
						+ "}" //
		);

		ClassDiagram mockDiagram = EasyMock.mock(MockType.STRICT, ClassDiagram.class);
		Capture<Class> classesCapture = Capture.newInstance(CaptureType.ALL);
		mockDiagram.addClass(EasyMock.capture(classesCapture));
		EasyMock.expectLastCall().times(2);
		mockDiagram.write(EasyMock.anyObject());

		DiagramFactory mockFactory = EasyMock.mock(MockType.STRICT, DiagramFactory.class);
		EasyMock.expect(mockFactory.createClassDiagram("test_detail")).andReturn(mockDiagram);
		
		EasyMock.replay(mockFactory, mockDiagram);

		CompilationTask task = CompilationTaskBuilder //
				.compilationTask(compiler) //
				.withFileManager(fileManager) //
				.withJavaFile(sourceTestClass1) //
				.withJavaFile(sourceTestClass2) //
				.build();

		ClassDiagramProcessor classDiagramProcessor = new ClassDiagramProcessor(mockFactory );
		task.setProcessors(Arrays.asList(classDiagramProcessor));
		task.call();
		
		EasyMock.verify(mockFactory, mockDiagram);
		List<Class> values = classesCapture.getValues();
		Assert.assertThat(values.size(), Matchers.is(2));
		Class class1 = values.get(0);
		Assert.assertThat(class1.getFields().get(0).getReferenceType(), Matchers.is("org.gaertner.test.TestClass2"));
	}

	private File createPath(File startDir, List<String> pathParts) {
		File classFile = startDir;
		for (String pathPart : pathParts) {
			classFile = new File(classFile, pathPart);
		}
		return classFile;
	}
}
