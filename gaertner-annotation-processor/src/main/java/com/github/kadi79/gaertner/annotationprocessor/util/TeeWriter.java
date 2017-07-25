package com.github.kadi79.gaertner.annotationprocessor.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

/**
 * <p>TeeWriter class.</p>
 * <p>distributes everything written on this Stream to all of the Streams given as constructor argument.</p>
 *
 * @since 0.0.1
 */
public class TeeWriter extends Writer {

	List<Writer> writers;
	
	/**
	 * <p>Constructor for TeeWriter.</p>
	 *
	 * @param writers one or more {@link java.io.Writer} objects. Everything written on this Writer gets distributed to all of the given writers.
	 * @since 0.0.2
	 */
	public TeeWriter(Writer... writers) {
		this.writers = Arrays.asList(writers);
	}

	/**
	 * <p>Constructor for TeeWriter.</p>
	 *
	 * @param lock a {@link java.lang.Object} object.
	 * @param writers a {@link java.io.Writer} object.
	 * @since 0.0.2
	 */
	public TeeWriter(Object lock, Writer... writers) {
		super(lock);
		this.writers = Arrays.asList(writers);
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException {
		for (Writer writer : writers) {
			writer.close();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void flush() throws IOException {
		for (Writer writer : writers) {
			writer.flush();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (Writer writer : writers) {
			writer.write(cbuf, off, len);
		}
	}

}
