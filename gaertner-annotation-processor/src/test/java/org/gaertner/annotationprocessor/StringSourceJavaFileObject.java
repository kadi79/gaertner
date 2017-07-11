package org.gaertner.annotationprocessor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;

import javax.tools.SimpleJavaFileObject;

public class StringSourceJavaFileObject extends SimpleJavaFileObject {
	
	String content;
	private Charset charset;
	
	public StringSourceJavaFileObject(String fullQualifiedName, String content) {
		this(fullQualifiedName, content, Charset.defaultCharset());
	}
	public StringSourceJavaFileObject(String fullQualifiedName, String content, Charset charset) {
		super(URI.create(fullQualifiedNameAsUri(fullQualifiedName)), Kind.SOURCE);
		this.content = content;
		this.charset = charset;
	}

	private static String fullQualifiedNameAsUri(String fullQualifiedName) {
		String nameInPathNotationn = fullQualifiedName.replaceAll("\\.", "/");
		String extension = Kind.SOURCE.extension;
		return nameInPathNotationn + extension;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return content;
	}
	
	public String getContent() {
		return content;
	}
	
	@Override
	public OutputStream openOutputStream() throws IOException {
		throw new IllegalStateException("No write access to File");
	}
	
	@Override
	public InputStream openInputStream() throws IOException {
		return new ByteArrayInputStream(getContent().getBytes(charset));
	}
	
	@Override
	public Writer openWriter() throws IOException {
		throw new IllegalStateException("No write access to File");
	}
}
