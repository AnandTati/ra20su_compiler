package com.ra20su.syntax.processor;

import java.util.ArrayList;
import java.util.List;

import com.ra20su.lexer.library.tokens.Operators;
import com.ra20su.lexer.objects.Token;
import com.ra20su.lexer.processors.LexicalProcessor;
import com.ra20su.utils.CommonUtils;
import com.ra20su.utils.TokenUtils;

public class Expresions {

	private LexicalProcessor lexicalProcessor = null;
	private Token token;

	boolean print = true;
	private String outputString = "";
	private List<String> outputStrings = null;

	public Expresions(LexicalProcessor lexicalProcessor, List<String> outputStrings) {
		super();
		if (CommonUtils.isEmpty(outputStrings)) {
			outputStrings = new ArrayList<>();
		} else
			this.outputStrings = outputStrings;
		this.lexicalProcessor = lexicalProcessor;
		token = lexicalProcessor.getNextToken();
	}

	public List<String> getOutPutStringList(boolean printOnScreen) {
		addtoOutput(outputString, printOnScreen);
		return outputStrings;
	}

	public void Conditions() {

		Expression();
		Relop();
		Expression();
	}

	public void Expression() {

		outputString = outputString + "<Expression>::=> ";
		Term();
		Expression_Prime();
	}

	public void Relop() {

		if (Operators.EQUAL_TO.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";

		} else if (Operators.GREATER_THAN.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";

		} else if (Operators.LESS_THAN.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";
		}
	}

	public void Expression_Prime() {
		outputString = outputString + "<Expression Prime>::=> ";
		if (Operators.ADDITION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + " + " + "\n";
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";
			Term();
		} else if (Operators.SUBSTRACTION.getValue().equalsIgnoreCase(token.getLexeme())) {
			outputString = outputString + "<Subtraction>" + "\n";
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";
			Term();
		} else if (Operators.ASSIGNMENT.getValue().equalsIgnoreCase(token.getLexeme())) {
			outputString = outputString + "<Assign>" + "\n";
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";
			Term();
		} else
			Empty();
	}

	public void Term() {
		outputString = outputString + "<Term>::=> ";
		Factor();
		Term_Prime();
	}

	public void Term_Prime() {
		outputString = outputString + "<Term Prime>::=> ";
		if (Operators.MULTIPLICATION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";
			Factor();
		} else if (Operators.DIVISION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";
			Factor();
		} else if (Operators.ADDITION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";
			Factor();
		} else if (Operators.SUBSTRACTION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";
			Factor();
		} else
			Empty();
	}

	public void Factor() {
		outputString = outputString + "<Factor>";
		Primary();
	}

	public void Primary() {

		if (TokenUtils.isInteger(token.getLexeme())) {
			outputString = outputString + "<Integer>" + "\n";
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";

		} else if (TokenUtils.isValidIdentifier(token.getLexeme())) {
			outputString = outputString + "<Identifier>" + "\n";
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";

		} else if (token.getLexeme().equalsIgnoreCase("true") || token.getLexeme().equalsIgnoreCase("false")) {
			token = lexicalProcessor.getNextToken();
			outputString = outputString + "\n" + CommonUtils.getFormattedString(token) + "\n";

		} else {
			outputString = outputString + "\n\n" + "Exception : Incorrect Expression at line number = " + token.getLineNumber() + "\n";
		}
	}

	public void Empty() {
		outputString = outputString + "epsilon" + "\n";
	}

	private void addtoOutput(String formattedTokenString, boolean printOnScreen) {
		outputStrings.add(formattedTokenString);
		if (printOnScreen)
			System.out.println(formattedTokenString);
	}

}
