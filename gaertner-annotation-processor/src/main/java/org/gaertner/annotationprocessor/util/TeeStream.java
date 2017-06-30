package org.gaertner.annotationprocessor.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TeeStream extends OutputStream {

	List<OutputStream> streams = new ArrayList<>();

	@Override
	public void write(int b) throws IOException {
		for (OutputStream stream : streams) {
			stream.write(b);
		}
	}

	@Override
	public void close() throws IOException {
		for (OutputStream stream : streams) {
			stream.close();
		}
	}

	@Override
	public void flush() throws IOException {
		for (OutputStream stream : streams) {
			stream.flush();
		}
	}

}
