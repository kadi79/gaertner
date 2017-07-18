package com.github.kadi79.gaertner.annotationprocessor.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TeeStream class.</p>
 *
 * @since 0.0.1
 */
public class TeeStream extends OutputStream {

	List<OutputStream> streams = new ArrayList<>();

	/** {@inheritDoc} */
	@Override
	public void write(int b) throws IOException {
		for (OutputStream stream : streams) {
			stream.write(b);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException {
		for (OutputStream stream : streams) {
			stream.close();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void flush() throws IOException {
		for (OutputStream stream : streams) {
			stream.flush();
		}
	}

}
