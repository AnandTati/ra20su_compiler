package com.ra20su.lexer.library.tokens;

import java.util.HashSet;
import java.util.Set;

public enum Operators {

	/* Assignment Operators */
	ASSIGNMENT("="),

//    ADD_AND_ASSIGNMENT("+="),
//
//    SUBSTRACT_AND_ASSIGNMENT("-="),
//
//    MULTIPLY_ADD_ASSIGNMENT("*="),
//
//    DIVIDE_AND_ASSIGNMENT("/="),
//
//    MODULUS_AND_ASSIGNMENT("%="),
//
//    LEFT_SHIFT_AND_ASSIGNMENT("<<="),
//
//    RIGHT_SHIFT_AND_ASSIGNMENT(">>="),
//
//    BITWISE_AND_AND_ASSIGMENT("&="),
//
//    BITWISE_OR_AND_ASSIGNMENT("|="),
//
//    BITWISE_XOR_ASSIGNMENT("^="),

	/* Arithmetic Operators */

	ADDITION("+"),

	SUBSTRACTION("-"),

	MULTIPLICATION("*"),

	DIVISION("/"),

	MODULUS("%"),

//    INCREAMENT("++"),
//
//    DECREMENT("--"),
//
	EQUAL_TO("=="),

	/* Relational Operators */
//    NOT_EQUAL_TO("!="),

	GREATER_THAN(">"),

	LESS_THAN("<"),

//    GREATER_THAN_EQUAL_TO(">="),

//    LESS_THAN_EQUAL_TO("<="),

	/* Bitwise Operators */

	BITWISE_AND("&"),

	BITWISE_OR("|"),

	BITWISE_XOR("^"),

	BITWISE_COMPLIMENT("~"),

//    LEFT_SHIFT("<<"),

//    RIGHT_SHIFT(">>"),

//    ZERO_FILL_RIGHT_SHIFT(">>>"),

	/* Logical Operators */

//    LOGICAL_AND("&&"),

//    LOGICAL_OR("||"),

	LOGICAL_NOT("!");

	String value;

	private Operators(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	private static final Set<String> OPERATORs = new HashSet<>();

	public static Set<String> getAllOperators() {
		if (OPERATORs.isEmpty()) {
			for (Operators operator : Operators.values()) {
				OPERATORs.add(operator.getValue());
			}
		}

		return OPERATORs;

	}

}
