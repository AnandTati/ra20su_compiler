package com.ra20su.syntax.processor;

import com.ra20su.lexer.objects.Token;
import com.ra20su.lexer.processors.LexicalProcessor;

public class While_Statement {
	private LexicalProcessor lexicalProcessor = null;
	private Token token;

	public While_Statement(LexicalProcessor lexicalProcessor) {
		super();
		this.lexicalProcessor = lexicalProcessor;
		token = lexicalProcessor.getNextToken();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
