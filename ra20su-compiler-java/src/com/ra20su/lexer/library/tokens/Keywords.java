package com.ra20su.lexer.library.tokens;

import java.util.HashSet;
import java.util.Set;

public enum Keywords {

	WHILE("while"),

	IF("if"),

	// ELSE("else"),

	FI("fi"),

	OTHERWISE("otherwise"),

	//FOR("for"),

	// DataTypes

	INTEGER("integer"),

	// CHAR("char"),

	// other
	GET("get"),

	PUT("put"),

	COMPOUND("Compound"),

	ASSIGN("Assign"),

	PRINT("Print"),

	SCAN("Scan"),

	BOOLEAN("boolean"),

	INT("integer");

	String value;

	private Keywords(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	private static final Set<String> KEYWORDS = new HashSet<>();

	public static Set<String> getAllKeywords() {
		if (KEYWORDS.isEmpty()) {
			for (Keywords keywaord : Keywords.values()) {
				KEYWORDS.add(keywaord.getValue());
			}
		}

		return KEYWORDS;

	}

}
