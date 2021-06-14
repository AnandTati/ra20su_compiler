package com.ra20su.utils;

import com.ra20su.lexer.library.tokens.Keywords;
import com.ra20su.lexer.library.tokens.Operators;
import com.ra20su.lexer.library.tokens.Separators;

public class TokenUtils {
	public static boolean isValidOperator(String token) {
		return Operators.getAllOperators().contains(token);
	}

	public static boolean isValidOperator(char token) {
		return isValidOperator(token + "");
	}

	public static boolean isValidSeparator(String token) {
		return Separators.getAllSeparators().contains(token);
	}

	public static boolean isValidSeparator(char token) {
		return isValidSeparator(token + "");
	}

	public static boolean isValidKeywaord(String token) {
		return Keywords.getAllKeywords().contains(token);
	}

	public static boolean isValidKeywaord(char token) {
		return isValidKeywaord(token + "");
	}

	public static boolean isValidIdentifier(String token) {
		if (CommonUtils.isEmpty(token))
			return false;
		else if (token.trim().isEmpty())
			return false;
		char[] charArr = token.toCharArray();
		int firstCharASCII = (int) charArr[0];
		if (!((firstCharASCII >= 65 && firstCharASCII <= 90) || (firstCharASCII >= 97 && firstCharASCII <= 122)))
			return false;
		for (int i = 1; i < charArr.length; i++) {

			int ascii = (int) charArr[i];
			if (!((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122) || ascii == 95))
				return false;
		}
		return true;
	}

	public static boolean isInteger(String token) {
		char[] charArr = token.toCharArray();
		for (char chr : charArr) {
			int ascii = (int) chr;
			if (!(ascii >= 48 && ascii <= 57))
				return false;

		}
		return true;
	}

	public static boolean isBoolean(String lexeme) {
		if (!CommonUtils.isEmpty(lexeme)) {
			return Boolean.parseBoolean(lexeme);
		}
		return false;
	}

}
