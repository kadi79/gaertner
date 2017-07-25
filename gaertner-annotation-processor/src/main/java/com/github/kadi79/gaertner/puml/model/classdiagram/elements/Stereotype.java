package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

import com.github.kadi79.gaertner.puml.model.Color;

public class Stereotype {

	private Optional<String> name;
	private Optional<Character> highlightCharacter;
	private Optional<Color> highlightColor;

	
	public Stereotype(Optional<String> name, Optional<Character> highlightCharacter, Optional<Color> highlightColor) {
		super();
		this.name = name;
		this.highlightCharacter = highlightCharacter;
		this.highlightColor = highlightColor;
	}

	public Stereotype(String name, Character highlightCharacter, Color highlightColor) {
		this(Optional.ofNullable(name), Optional.ofNullable(highlightCharacter), Optional.ofNullable(highlightColor));
	}

	public void write(Writer out) throws IOException {
		if (allEmpty(name, highlightCharacter)) return;
		out.write("<< ");
		if (highlightCharacter.isPresent() || highlightColor.isPresent()) {
			out.write("(");
			out.write(highlightCharacter.map(c-> String.valueOf(c)).orElse(""));
			if (highlightColor.isPresent()) {
				out.write(",");
				highlightColor.get().write(out);
			}
			out.write(")");
			out.write(name.map(s->{return " ";}).orElse(""));
		}
		out.write(name.orElse(""));
		out.write(" >>");
	}

	private boolean allEmpty(Optional<?>... objects) {
		for (Optional<?> object : objects) {
			if(object.isPresent()) return false;
		}
		return true;
	}
}
