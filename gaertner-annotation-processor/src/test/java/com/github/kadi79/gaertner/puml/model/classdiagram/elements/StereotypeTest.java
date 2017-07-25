package com.github.kadi79.gaertner.puml.model.classdiagram.elements;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.github.kadi79.gaertner.puml.model.Color;

public class StereotypeTest {

	private static final String STEREOTYPE_NAME = "Stereotype";
	private static final char STEREOTYPE_HIGHLIGHT_CHAR = 'Z';
	private static String STEREOTYPE_HIGHLIGHT_COLOR = "#abcdef";

	@Test
	public void testEmptyStereotype() throws Exception {
		Stereotype stereotype = new Stereotype((String) null, null, null);

		String result = stereotypeToString(stereotype);

		assertThat(result, isEmptyString());
	}

	@Test
	public void testOnlyName() throws Exception {
		Stereotype stereotype = new Stereotype(STEREOTYPE_NAME, null, null);

		String result = stereotypeToString(stereotype);

		assertThat(result, is("<< " + STEREOTYPE_NAME + " >>"));
	}

	@Test
	public void testOnlyHighlightCharacter() throws Exception {
		Stereotype stereotype = new Stereotype(null, Character.valueOf(STEREOTYPE_HIGHLIGHT_CHAR), null);

		String result = stereotypeToString(stereotype);

		assertThat(result, is("<< (" + STEREOTYPE_HIGHLIGHT_CHAR + ") >>"));
	}

	@Test
	public void testOnlyHighlightColor() throws Exception {
		Stereotype stereotype = new Stereotype(null, null, new Color(STEREOTYPE_HIGHLIGHT_COLOR));

		String result = stereotypeToString(stereotype);

		assertThat(result, Matchers.isEmptyString());
	}

	@Test
	public void testComplete() throws Exception {
		Stereotype stereotype = new Stereotype(STEREOTYPE_NAME, Character.valueOf(STEREOTYPE_HIGHLIGHT_CHAR),
				new Color(STEREOTYPE_HIGHLIGHT_COLOR));

		String result = stereotypeToString(stereotype);

		assertThat(result, is("<< (" + STEREOTYPE_HIGHLIGHT_CHAR + "," + STEREOTYPE_HIGHLIGHT_COLOR + ") "
				+ STEREOTYPE_NAME + " >>"));
	}

	private String stereotypeToString(Stereotype stereotype) throws IOException {
		Writer out = new StringWriter();
		stereotype.write(out);
		out.close();
		return out.toString();
	}
}
