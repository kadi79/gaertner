package org.gaertner.annotationprocessor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.gaertner.annotationprocessor.util.TeeWriter;
import org.junit.Test;

public class ClassDiagramProcessorTest {

	@Test
	public void testName() throws Exception {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StringWriter stringWriter = new StringWriter();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out, "UTF-8");
		Writer writer = new TeeWriter(outputStreamWriter, stringWriter);
		Iterable<? extends JavaFileObject> classFiles = Arrays.asList(new StringSourceJavaFileObject("org.gaertner.test.EmptyClass", "package org.gaertner.test;\n" + 
				"\n" + 
				"import org.gaertner.annotations.UmlClassDiagram;\n" + 
				"\n" + 
				"@UmlClassDiagram(filename=\"test_detail\")\n" + 
				"@UmlClassDiagram(filename=\"test\", fields= {}, methods={})\n" + 
				"public class EmptyClass {\n" + 
				"	int g;\n" + 
				"\n" + 
				"}\n" + 
				""));
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Path targetDir = Files.createTempDirectory("target");
		File classTarget = new File(targetDir.toFile(), "classes");
		Files.createDirectory(classTarget.toPath());
		File generatedSourcesTarget = new File(targetDir.toFile(), "generatedSources");
		Files.createDirectory(generatedSourcesTarget.toPath());
		fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(classTarget));
		fileManager.setLocation(StandardLocation.SOURCE_OUTPUT, Arrays.asList(generatedSourcesTarget));
		CompilationTask task = compiler.getTask(writer, fileManager, null, null, null, classFiles);
		task.setProcessors(Arrays.asList(new ClassDiagramProcessor()));
		task.call();
		
		Files.walkFileTree(targetDir, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				if (exc != null) throw exc;
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
