package com.ra20su.lexer.objects;

import com.ra20su.lexer.library.tokens.TokenName;

public class Token {

	TokenName name;

	String lexeme;

	int lineNumber;

	int columnNumber;

	public Token() {
		super();
	}

	public Token(TokenName name, String lexeme, int lineNumber) {
		super();
		this.name = name;
		this.lexeme = lexeme;
		this.lineNumber = lineNumber;
	}

	public Token(TokenName name, String lexeme, int lineNumber, int columnNumber) {
		super();
		this.name = name;
		this.lexeme = lexeme;
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columnNumber;
		result = prime * result + ((lexeme == null) ? 0 : lexeme.hashCode());
		result = prime * result + lineNumber;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (columnNumber != other.columnNumber)
			return false;
		if (lexeme == null) {
			if (other.lexeme != null)
				return false;
		} else if (!lexeme.equals(other.lexeme))
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		if (name != other.name)
			return false;
		return true;
	}

	public TokenName getName() {
		return name;
	}

	public void setName(TokenName name) {
		this.name = name;
	}

	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public String getNameString() {
		return name.getValue();
	}

	@Override
	public String toString() {
		return "Token [name=" + name + ", lexeme=" + lexeme + ", lineNumber=" + lineNumber + ", columnNumber=" + columnNumber + "]";
	}

}
