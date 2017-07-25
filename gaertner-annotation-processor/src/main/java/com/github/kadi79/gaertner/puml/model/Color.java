package com.github.kadi79.gaertner.puml.model;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>Color class.</p>
 *
 * @since 0.0.2
 */
public class Color {

	String color;
	
	/**
	 * <p>Constructor for Color.</p>
	 *
	 * @param color a {@link java.lang.String} object.
	 */
	public Color(String color) {
		this.color = color;
	}

	/**
	 * <p>asString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String asString() {
		return color;
	}
	
	/**
	 * <p>write.</p>
	 *
	 * @param out a {@link java.io.Writer} object.
	 * @throws java.io.IOException if any.
	 */
	public void write(Writer out) throws IOException {
		out.write(color);
	}
}
