package org.gaertner.annotationprocessor;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class TeeWriter extends Writer {

	List<Writer> writers;
	
	public TeeWriter(Writer... writers) {
		this.writers = Arrays.asList(writers);
	}

	public TeeWriter(Object lock, Writer... writers) {
		super(lock);
		this.writers = Arrays.asList(writers);
	}

	@Override
	public void close() throws IOException {
		for (Writer writer : writers) {
			writer.close();
		}
	}

	@Override
	public void flush() throws IOException {
		for (Writer writer : writers) {
			writer.flush();
		}
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (Writer writer : writers) {
			writer.write(cbuf, off, len);
		}
	}

}
