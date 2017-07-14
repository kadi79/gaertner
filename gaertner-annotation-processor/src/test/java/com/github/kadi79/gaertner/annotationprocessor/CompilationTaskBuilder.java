package com.github.kadi79.gaertner.annotationprocessor;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

public class CompilationTaskBuilder {

	private JavaCompiler compiler;
	private Writer out;
	private JavaFileManager fileManager;
	private DiagnosticListener<? super JavaFileObject> diagnosticListener;
	private Optional<List<String>> options = Optional.empty();
	private Optional<List<String>> classes = Optional.empty();
	private Optional<List<JavaFileObject>> compilationUnits = Optional.empty();

	protected CompilationTaskBuilder(JavaCompiler compiler) {
		this.compiler = compiler;
	}

	public static CompilationTaskBuilder compilationTask(JavaCompiler compiler) {
		return new CompilationTaskBuilder(compiler);
	}
	
	public CompilationTaskBuilder withConsoleOut(Writer out) {
		this.out = out;
		return this;
	}
	
	public CompilationTaskBuilder withFileManager(JavaFileManager fileManager) {
		this.fileManager = fileManager;
		return this;
	}
	
	public CompilationTaskBuilder withDiagnosticListener(DiagnosticListener<? super JavaFileObject> diagnosticListener) {
		this.diagnosticListener = diagnosticListener;
		return this;
	}
	
	public CompilationTaskBuilder withOption(String option) {
		if (!options.isPresent()) options = Optional.of(new ArrayList<>());
		options.get().add(option);
		return this;
	}
	
	public CompilationTaskBuilder withClassToProcess(String className) {
		if (!classes.isPresent()) classes = Optional.of(new ArrayList<>());
		classes.get().add(className);
		return this;
	}
	
	public CompilationTaskBuilder withJavaFile(JavaFileObject javaFile) {
		if (!compilationUnits.isPresent()) compilationUnits = Optional.of(new ArrayList<>());
		compilationUnits.get().add(javaFile);
		return this;
	}
	
	public CompilationTask build() {
		return compiler.getTask(out, fileManager, diagnosticListener, options.orElse(null), classes.orElse(null), compilationUnits.orElse(null));
	}
}
