package com.ra20su.lexer.library.tokens;

import java.util.HashSet;
import java.util.Set;

public enum Separators {

	PARANTHESIS_OPENING("("),

	PARANTHESIS_CLOSING(")"),

	CURLY_BRACES_OPENING("{"),

	CURLY_BRACES_CLOSING("}"),

	SQUARE_BRACES_OPENING("["),

	SQUARE_BRACES_CLOSING("]"),

	SEMICOLON(";"),

	COMMA(","),

	PERIOD("."),

	DOLLAR("$");

	String value;

	private Separators(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	private static final Set<String> SEPARATORS = new HashSet<>();

	public static Set<String> getAllSeparators() {

		if (SEPARATORS.isEmpty()) {
			Separators[] separators = Separators.values();
			for (Separators separator : separators) {
				SEPARATORS.add(separator.getValue());
			}
		}
		return SEPARATORS;

	}
}
