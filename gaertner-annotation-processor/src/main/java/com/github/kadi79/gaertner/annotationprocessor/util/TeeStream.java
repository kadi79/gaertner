package com.github.kadi79.gaertner.annotationprocessor.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>TeeStream class.</p>
 * <p>distributes everything written on this Stream to all of the Streams given as constructor argument.</p>
 *
 * @since 0.0.1
 */
public class TeeStream extends OutputStream {

	List<OutputStream> streams = new ArrayList<>();
	
	/**
	 * <p>Constructor for TeeStream.</p>
	 *
	 * @param outputStreams one or more {@link java.io.Writer} objects. Everything written on this Stream gets distributed to all of the given outputStreams.
	 */
	public TeeStream(OutputStream...outputStreams) {
		this.streams.addAll(Arrays.asList(outputStreams));
	}

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
