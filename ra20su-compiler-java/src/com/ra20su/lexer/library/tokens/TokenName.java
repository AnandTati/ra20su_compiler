package com.ra20su.lexer.library.tokens;

public enum TokenName {
	OPERATOR("Operator"),

	SEPARATOR("Separator"),

	KEYWAORD("Keyword"),

	IDENTIFIER("Identifier"),

	INTEGER("Integer"),

	STRING("String"),

	BOOLEAN("boolean"),

	INVALID_TOKEN("Invalid Token");

	String value;

	private TokenName(java.lang.String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
