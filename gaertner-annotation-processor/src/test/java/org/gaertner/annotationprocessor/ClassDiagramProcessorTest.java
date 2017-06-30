package org.gaertner.annotationprocessor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.gaertner.annotationprocessor.util.TeeWriter;
import org.junit.Ignore;
import org.junit.Test;

public class ClassDiagramProcessorTest {

	@Ignore
	@Test
	public void runAnnotationProcessor() throws Exception {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StringWriter stringWriter = new StringWriter();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out, "UTF-8");
		Writer writer = new TeeWriter(outputStreamWriter, stringWriter);
		Iterable<? extends JavaFileObject> classFiles = getClassFiles("src/test/emptyClassTest");
		CompilationTask task = compiler.getTask(writer, null, null, null, null, classFiles);
		task.setProcessors(Arrays.asList(new ClassDiagramProcessor()));
		task.call();
	}

	private Iterable<? extends JavaFileObject> getClassFiles(String sourcePath) throws IOException {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, Locale.GERMANY, Charset.forName("utf-8"));
		fileManager.setLocation(StandardLocation.SOURCE_PATH, Arrays.asList(new File(sourcePath)));
		return fileManager.list(StandardLocation.SOURCE_PATH, "", Collections.singleton(Kind.SOURCE), true);
	}
}
