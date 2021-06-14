package com.ra20su.lexer.processors;

import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Field;

public class OperatorsWithReflection {
	// Assignment Operators
	public String ASSIGNMENT = "=";

	public String ADD_AND_ASSIGNMENT = "+=";

	public String SUBSTRACT_AND_ASSIGNMENT = "-=";

	public String MULTIPLY_ADD_ASSIGNMENT = "*=";

	public String DIVIDE_AND_ASSIGNMENT = "/=";

	public String MODULUS_AND_ASSIGNMENT = "%=";

	public String LEFT_SHIFT_AND_ASSIGNMENT = "<<=";

	public String RIGHT_SHIFT_AND_ASSIGNMENT = ">>=";

	public String BITWISE_AND_AND_ASSIGMENT = "&=";

	public String BITWISE_OR_AND_ASSIGNMENT = "|=";

	public String BITWISE_XOR_ASSIGNMENT = "^=";

	// Arithmetic Operators

	public String ADDITION = "+";

	public String SUBSTRACTION = "-";

	public String MULTIPLICATION = "*";

	public String DIVISION = "/";

	public String MODULUS = "%";

	public String INCREAMENT = "++";

	public String DECREMENT = "--";

	// Relational Operators public String EQUAL_TO = "==";

	public String NOT_EQUAL_TO = "!=";

	public String GREATER_THAN = ">";

	public String LESS_THAN = "<";

	public String GREATER_THAN_EQUAL_TO = ">=";

	public String LESS_THAN_EQUAL_TO = "<=";

	// Bitwise Operators
	public String BITWISE_AND = "&";

	public String BITWISE_OR = "|";

	public String BITWISE_XOR = "^";

	public String BITWISE_COMPLIMENT = "~";

	public String LEFT_SHIFT = "<<";

	public String RIGHT_SHIFT = ">>";

	public String ZERO_FILL_RIGHT_SHIFT = ">>>";

	// Logical Operators

	public String LOGICAL_AND = "&&";

	public String LOGICAL_OR = "||";

	public String LOGICAL_NOT = "!";

	public static final Set<String> operators = new HashSet<>();

	public static Set<String> getALLOperators() throws IllegalArgumentException, IllegalAccessException {

		if (operators.isEmpty()) {
			Field[] fields = OperatorsWithReflection.class.getDeclaredFields();
			for (Field field : fields) {

				Class<?> x = field.getType();
				if (!x.equals(String.class))
					continue;
				String value = (String) field.get(new Object());
				operators.add(value);
			}
		}

		return operators;

	}

}
