package com.ra20su.semantic.processor;

import java.util.List;

import com.ra20su.exceptions.SemanticException;
import com.ra20su.lexer.library.tokens.Operators;
import com.ra20su.lexer.library.tokens.Separators;
import com.ra20su.lexer.objects.Token;
import com.ra20su.lexer.processors.LexicalProcessor;
import com.ra20su.semantic.InstructionTable;
import com.ra20su.semantic.SymbolTable;
import com.ra20su.semantic.objects.Symbol;
import com.ra20su.utils.TokenUtils;

public class StatementSemanticAnalyzer {
	SymbolTable symbolTable = null;
	InstructionTable instructionTable = null;
	Symbol symbolIdentifierTemp;

	public StatementSemanticAnalyzer(SymbolTable symbolTable, InstructionTable instructionTable, LexicalProcessor lexicalProcessor) {
		super();
		this.symbolTable = symbolTable;
		this.instructionTable = instructionTable;
		this.lexicalProcessor = lexicalProcessor;
		token = lexicalProcessor.getNextToken();
	}

	private LexicalProcessor lexicalProcessor = null;
	private Token token;
	private Token token_save_tmp;
	// private print = true;
	private String outputString = "";
	private List<String> outputStrings = null;

	public StatementSemanticAnalyzer(LexicalProcessor lexicalProcessor) {
		super();
		this.lexicalProcessor = lexicalProcessor;
		token = lexicalProcessor.getNextToken();
	}

	public void A() throws SemanticException {

		if (symbolTable.contains(token.getLexeme())) {
			token_save_tmp = token;
			symbolIdentifierTemp = symbolTable.getSymbol(token.getLexeme());

			token = lexicalProcessor.getNextToken();
			if (token.getLexeme().equals("=")) {
				token = lexicalProcessor.getNextToken();
				if (!token_save_tmp.getLexeme().equalsIgnoreCase(token.getLexeme()))
					instructionTable.addInstrustion("PUSHM", symbolIdentifierTemp.getMemLocation() + "");
				E();
				symbolIdentifierTemp = symbolTable.getSymbol(token_save_tmp.getLexeme());
				instructionTable.addInstrustion("POPM", symbolIdentifierTemp.getMemLocation() + "");

			} else
				System.out.println("Error: = required");
		} else {
			System.out.println("Error: ID required");
		}
	}

	private void E() throws SemanticException {
		T();
		E_prime();

	}

	private void E_prime() throws SemanticException {

		if (token == null) {

		} else if (Operators.ADDITION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			T();
			instructionTable.addInstrustion("ADD", "");

		} else if (Operators.SUBSTRACTION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			T();
			instructionTable.addInstrustion("SUB", "");

		} else if (Operators.MULTIPLICATION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			T();
			instructionTable.addInstrustion("MUL", "");

		} else if (Operators.DIVISION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			T();
			instructionTable.addInstrustion("DIV", "");

		}
	}

	private void T() throws SemanticException {

		F();
		T_Prime();

	}

	private void F() throws SemanticException {
		if (symbolTable.contains(token.getLexeme())) {
			symbolIdentifierTemp = symbolTable.getSymbol(token.getLexeme());
			instructionTable.addInstrustion("PUSHM", symbolIdentifierTemp.getMemLocation() + "");

			token = lexicalProcessor.getNextToken();
		} else if (TokenUtils.isInteger(token.getLexeme())) {
			instructionTable.addInstrustion("PUSHI", token.getLexeme());

			token = lexicalProcessor.getNextToken();
		} else
			System.out.println("ID expected or not defined");
	}

	private void T_Prime() throws SemanticException {
		if (token == null) {

		} else if (Operators.ADDITION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			F();
			instructionTable.addInstrustion("ADD", "");
			T_Prime();
		} else if (Operators.SUBSTRACTION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			F();
			instructionTable.addInstrustion("SUB", "");
			T_Prime();
		} else if (Operators.MULTIPLICATION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			F();
			instructionTable.addInstrustion("MUL", "");
			T_Prime();
		} else if (Operators.DIVISION.getValue().equalsIgnoreCase(token.getLexeme())) {
			token = lexicalProcessor.getNextToken();
			F();
			instructionTable.addInstrustion("DIV", "");
			T_Prime();
		}

	}

	public void Compare() throws SemanticException {
		E();

		if (Relop()) {
			token_save_tmp = token;
			token = lexicalProcessor.getNextToken();
			E();
			switch (token_save_tmp.getLexeme()) {
			case ">":
				instructionTable.addInstrustion("GRT", "");

				break;
			case "<":
				instructionTable.addInstrustion("LES", "");
				break;
			case "==":
				instructionTable.addInstrustion("EQU", "");
				break;
			}
		} else
			System.out.println("R token expected");

	}

	public boolean Relop() {

		if (Operators.EQUAL_TO.getValue().equalsIgnoreCase(token.getLexeme())) {
			return true;

		} else if (Operators.GREATER_THAN.getValue().equalsIgnoreCase(token.getLexeme())) {
			return true;

		} else if (Operators.LESS_THAN.getValue().equalsIgnoreCase(token.getLexeme())) {
			return true;

		}
		return false;
	}

	public void interaction() throws SemanticException {
		if ("put".equalsIgnoreCase(token.getLexeme())) {
			// instructionTable.addInstrustion("STDOUT", " ");
			token = lexicalProcessor.getNextToken();
			if (Separators.PARANTHESIS_OPENING.getValue().equalsIgnoreCase(token.getLexeme())) {
				token = lexicalProcessor.getNextToken();
				if (symbolTable.contains(token.getLexeme())) {
					symbolIdentifierTemp = symbolTable.getSymbol(token.getLexeme());
					instructionTable.addInstrustion("PUSHM", symbolIdentifierTemp.getMemLocation() + "");
					instructionTable.addInstrustion("STDOUT", " ");
				} else {
					System.out.println("Error: ID required");
				}
				token = lexicalProcessor.getNextToken();
				if (Separators.PARANTHESIS_CLOSING.getValue().equalsIgnoreCase(token.getLexeme())) {
					token = lexicalProcessor.getNextToken();
				} else
					System.out.println(") missing ");
			} else
				System.out.println("( missing ");
		} else if ("get".equalsIgnoreCase(token.getLexeme())) {
			instructionTable.addInstrustion("STDIN ", " ");
			token = lexicalProcessor.getNextToken();
			if (Separators.PARANTHESIS_OPENING.getValue().equalsIgnoreCase(token.getLexeme())) {
				token = lexicalProcessor.getNextToken();
				if (symbolTable.contains(token.getLexeme())) {
					symbolIdentifierTemp = symbolTable.getSymbol(token.getLexeme());
					instructionTable.addInstrustion("POPM", symbolIdentifierTemp.getMemLocation() + "");
				} else {
					System.out.println("Error: ID required");
				}
				token = lexicalProcessor.getNextToken();
				if (Separators.PARANTHESIS_CLOSING.getValue().equalsIgnoreCase(token.getLexeme())) {
					token = lexicalProcessor.getNextToken();
				} else
					System.out.println(") missing ");
			} else
				System.out.println("( missing ");
		}
	}

	public List<String> getOutPutStringList(boolean printOnScreen) {
		addtoOutput(outputString, printOnScreen);
		return outputStrings;
	}

	private void addtoOutput(String formattedTokenString, boolean printOnScreen) {
		outputStrings.add(formattedTokenString);
		if (printOnScreen)
			System.out.println(formattedTokenString);
	}
}
