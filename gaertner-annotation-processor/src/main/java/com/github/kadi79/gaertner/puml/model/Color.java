package com.github.kadi79.gaertner.puml.model;

import java.io.IOException;
import java.io.Writer;

public class Color {

	String color;
	
	public Color(String color) {
		this.color = color;
	}

	public String asString() {
		return color;
	}
	
	public void write(Writer out) throws IOException {
		out.write(color);
	}
}
