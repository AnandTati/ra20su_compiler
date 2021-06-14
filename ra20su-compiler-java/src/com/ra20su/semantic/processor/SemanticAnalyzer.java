package com.ra20su.semantic.processor;

import java.util.Stack;

import com.ra20su.exceptions.SemanticException;
import com.ra20su.lexer.library.tokens.Keywords;
import com.ra20su.lexer.library.tokens.Separators;
import com.ra20su.lexer.library.tokens.TokenName;
import com.ra20su.lexer.objects.Token;
import com.ra20su.lexer.processors.LexicalProcessor;
import com.ra20su.semantic.InstructionTable;
import com.ra20su.semantic.SymbolTable;
import com.ra20su.semantic.objects.Instruction;
import com.ra20su.semantic.objects.InstructionCounter;
import com.ra20su.semantic.objects.MemoryLocationProvider;
import com.ra20su.semantic.objects.Symbol;
import com.ra20su.utils.CommonUtils;
import com.ra20su.utils.Pair;

public class SemanticAnalyzer {

	private LexicalProcessor lexicalProcessor = null;

	private Stack<Pair<Token, Instruction>> whileStack = new Stack<>();

	private boolean print = true;

	private SymbolTable symbolTable = new SymbolTable();

	private InstructionTable instructionTable = new InstructionTable();

	private Token nextTokenGlobal;

	public SemanticAnalyzer(LexicalProcessor lexicalProcessor) {
		super();
		this.lexicalProcessor = lexicalProcessor;
	}

	public SemanticAnalyzer(LexicalProcessor lexicalProcessor, boolean printOnScreen) {
		super();
		this.print = printOnScreen;
		this.lexicalProcessor = lexicalProcessor;
		MemoryLocationProvider.reset();
		InstructionCounter.reset();
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public InstructionTable getInstructionTable() {
		return instructionTable;
	}

	public void process() throws Exception {
		lexicalProcessor.refresh();
		pass1();
		lexicalProcessor.refresh();
		pass2();
	}

	private void pass1() throws SemanticException {

		if (!("$".equals(getNextToken().getLexeme()) && "$".equals(getNextToken().getLexeme()))) {
			throw new SemanticException("No Starting '$$' token found ");
		}
		Token token = null;
		while ((token = getNextToken()) != null) {
			switch (token.getName()) {
			case KEYWAORD: {

				Symbol symbol = new Symbol();
				if (Keywords.INTEGER.getValue().equals(token.getLexeme())) {
					symbol.setType(token.getLexeme());
					Token nextToken = getNextToken();
					if (TokenName.IDENTIFIER.equals(nextToken.getName())) {
						symbol.setSymbolName(nextToken.getLexeme());
						this.symbolTable.addSymbol(symbol);
						nextToken = getNextToken();
						if (TokenName.SEPARATOR.equals(nextToken.getName())) {
							if (";".equals(nextToken.getLexeme())) {
							} else
								throw new SemanticException(
										"Exception : Invalid separator at line = " + nextToken.getLineNumber());

						} else if ("=".equals(nextToken.getLexeme())) {
							moveTokenBack(1);
							moveTokenBack(1);
							StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
									instructionTable, lexicalProcessor);
							statmentAnalyzer.A();
						} else {
							throw new SemanticException("Exception : Invalid token '" + nextToken.getLexeme()
									+ "' at line number = " + nextToken.getLineNumber());
						}
					} else
						throw new SemanticException("Exception : Expected an " + TokenName.IDENTIFIER.getValue()
								+ " after keywaord 'int' at line number = " + nextToken.getLineNumber());
				}
			}
			default:
				break;
			}
		}
	}

	public void pass2() throws Exception {

		boolean endOfFile = false;
		boolean startOfFile = false;
		if ("$".equals(getNextToken().getLexeme())) {
			if ("$".equals(getNextToken().getLexeme())) {
				startOfFile = true;
				Token token = null;
				while ((token = getNextToken()) != null) {
					processToken(token);
					if ("$".equals(token.getLexeme())) {
						if ("$".equals(token.getLexeme())) {
							endOfFile = true;

						}
					}
				}

			}
		}
		if (!startOfFile)
			addtoOutput("\n EXCEPTION : Code must start with marker $$\"");
		if (!endOfFile)
			addtoOutput("\n EXCEPTION : Code must end with marker $$");
	}

	private void processToken(Token token) throws Exception {
		addtoOutput(System.lineSeparator());
		switch (token.getName()) {
		case KEYWAORD: {
			String lexeme = token.getLexeme();
			switch (lexeme) {
			case "if": {

				nextTokenGlobal = getNextToken();
				if (nextTokenGlobal.getName().equals(TokenName.SEPARATOR)
						&& nextTokenGlobal.getLexeme().equals(Separators.PARANTHESIS_OPENING.getValue())) {
					StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
							instructionTable, lexicalProcessor);
					statmentAnalyzer.Compare();
					Pair<Token, Instruction> ifJUPMZ = new Pair<Token, Instruction>(token,
							new Instruction("JUMPZ", ""));
					instructionTable.addInstrustion(ifJUPMZ.getV());
					whileStack.push(ifJUPMZ);

				} else
					throw new SemanticException("Exception : Expected " + TokenName.SEPARATOR.getValue() + " '"
							+ Separators.PARANTHESIS_OPENING.getValue() + "' after Keyword " + Keywords.WHILE.getValue()
							+ "at line number  = " + token.getLineNumber());
				nextTokenGlobal = getNextToken();
				while (!(Keywords.OTHERWISE.getValue().equals(nextTokenGlobal.getLexeme())
						|| Keywords.FI.getValue().equals(nextTokenGlobal.getLexeme()))) {
					if (Keywords.WHILE.getValue().equalsIgnoreCase(nextTokenGlobal.getLexeme())) {
						processToken(nextTokenGlobal);
						if ("}".equalsIgnoreCase(nextTokenGlobal.getLexeme()))
							nextTokenGlobal = getNextToken();

					} else {
						moveTokenBack(1);
						StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
								instructionTable, lexicalProcessor);
						statmentAnalyzer.A();
						nextTokenGlobal = getNextToken();
					}
				}
				if (Keywords.OTHERWISE.getValue().equals(nextTokenGlobal.getLexeme())) {
					if (whileStack.empty())
						throw new SemanticException("ERROR occured while processing 'otherwise' at line number "
								+ nextTokenGlobal.getLineNumber()
								+ " :   no corresponding 'if' condition found, stack is empty");
					Pair<Token, Instruction> popedIfJUMPZ = whileStack.pop();
					if (Keywords.IF.getValue().equalsIgnoreCase(popedIfJUMPZ.getK().getLexeme())) {
						popedIfJUMPZ.getV()
								.setAttribute((InstructionCounter.getCurrentStatusOfInstructionount() + 1) + "");
					} else
						throw new SemanticException("ERROR occured while processing 'otherwise' at line number "
								+ nextTokenGlobal.getLineNumber()
								+ " :   no corresponding 'if' condition found, while stac knot empty");

					// this jump before 'otherwise' to jump to fi
					Instruction instructionJUMPbeforeOtherWise = new Instruction("JUMP", "");
					whileStack.push(new Pair<Token, Instruction>(nextTokenGlobal, instructionJUMPbeforeOtherWise));
					instructionTable.addInstrustion(instructionJUMPbeforeOtherWise);
					nextTokenGlobal = getNextToken();
					while (!Keywords.FI.getValue().equals(nextTokenGlobal.getLexeme())) {
						if (Keywords.WHILE.getValue().equalsIgnoreCase(nextTokenGlobal.getLexeme())) {
							processToken(nextTokenGlobal);
							if ("}".equalsIgnoreCase(nextTokenGlobal.getLexeme()))
								nextTokenGlobal = getNextToken();
						} else {
							moveTokenBack(1);
							StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
									instructionTable, lexicalProcessor);
							statmentAnalyzer.A();
							nextTokenGlobal = getNextToken();
						}
					}
					if (Keywords.FI.getValue().equals(nextTokenGlobal.getLexeme())) {
						instructionJUMPbeforeOtherWise
								.setAttribute(InstructionCounter.getCurrentStatusOfInstructionount() + "");
						Pair<Token, Instruction> popedInstructionJUMPbeforeOtherWise = whileStack.pop();
						if (!"JUMP".equalsIgnoreCase(popedInstructionJUMPbeforeOtherWise.getV().getInstruction())) {
							throw new SemanticException(
									"ERROR occure while analyzing if condition : no JUMP instruction found in stack");
						}

					} else
						throw new SemanticException("ERROR occured while processing 'otherwise' at line number "
								+ nextTokenGlobal.getLineNumber() + " :   no corresponding 'fi' found");
				} else if (Keywords.FI.getValue().equals(nextTokenGlobal.getLexeme())) {
					if (whileStack.empty())
						throw new SemanticException(
								"ERROR occured while processing 'fi' at line number " + nextTokenGlobal.getLineNumber()
										+ " :   no corresponding 'if' condition found, stack is empty");
					Pair<Token, Instruction> popedIfJUMPZ = whileStack.pop();
					if (Keywords.IF.getValue().equalsIgnoreCase(popedIfJUMPZ.getK().getLexeme())) {
						popedIfJUMPZ.getV().setAttribute(InstructionCounter.getCurrentStatusOfInstructionount() + "");
					} else
						throw new SemanticException(
								"ERROR occured while processing 'fi' at line number " + nextTokenGlobal.getLineNumber()
										+ " :   no corresponding 'if' condition found, while stack is not empty");

				}

			}
				break;

			case "otherwise": {

			}
				break;

			case "fi": {

			}

				break;
			case "get": {
				lexicalProcessor.moveOneTokenBack();
				StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
						instructionTable, lexicalProcessor);
				statmentAnalyzer.interaction();
			}
				break;
			case "put": {
				lexicalProcessor.moveOneTokenBack();
				StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
						instructionTable, lexicalProcessor);
				statmentAnalyzer.interaction();
			}
				break;
			case "integer": {

				while (!";".equals(token.getLexeme())) {
					token = getNextToken();
				}

			}
				break;
			case "while": {

				Instruction instructionLabel = new Instruction("LABEL", "");
				instructionTable.addInstrustion(instructionLabel);
				whileStack.push(new Pair<Token, Instruction>(token, instructionLabel));
				this.nextTokenGlobal = getNextToken();
				if (nextTokenGlobal.getName().equals(TokenName.SEPARATOR)
						&& nextTokenGlobal.getLexeme().equals(Separators.PARANTHESIS_OPENING.getValue())) {
					StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
							instructionTable, lexicalProcessor);
					statmentAnalyzer.Compare();
				} else
					throw new SemanticException("Exception : Expected " + TokenName.SEPARATOR.getValue() + " '"
							+ Separators.PARANTHESIS_OPENING.getValue() + "' after Keyword " + Keywords.WHILE.getValue()
							+ " at line number = " + token.getLineNumber());

				Pair<Token, Instruction> whilePair = whileStack.lastElement();
				if ("LABEL".equalsIgnoreCase(whilePair.getV().getInstruction())) {
					Instruction instructionJUMPZ = new Instruction("JUMPZ", "");
					instructionTable.addInstrustion(instructionJUMPZ);
					whileStack.push(new Pair<Token, Instruction>(token, instructionJUMPZ));
				}

				nextTokenGlobal = getNextToken();
				if (Separators.CURLY_BRACES_OPENING.getValue().equals(nextTokenGlobal.getLexeme())) {
					nextTokenGlobal = getNextToken();
					if (Keywords.IF.getValue().equalsIgnoreCase(nextTokenGlobal.getLexeme())) {
						processToken(nextTokenGlobal);
					} else {
						moveTokenBack(1);
						StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
								instructionTable, lexicalProcessor);
						statmentAnalyzer.A();
					}
					while (!Separators.CURLY_BRACES_CLOSING.getValue()
							.equals((nextTokenGlobal = getNextToken()).getLexeme())) {
						if (Keywords.IF.getValue().equalsIgnoreCase(nextTokenGlobal.getLexeme())) {
							processToken(nextTokenGlobal);
						} else {
							moveTokenBack(1);
							StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable,
									instructionTable, lexicalProcessor);
							statmentAnalyzer.A();
						}
					}

					if (Separators.CURLY_BRACES_CLOSING.getValue().equals(nextTokenGlobal.getLexeme())) {

						Instruction instructionJUMP = new Instruction("JUMP", "");
						Pair<Token, Instruction> whileStackPopJUMPZPair = whileStack.pop();
						Instruction popedJUMPZInstruction = whileStackPopJUMPZPair.getV();
						if (!popedJUMPZInstruction.getInstruction().equals("JUMPZ"))
							throw new Exception(
									"Error while handling while stack for while loop : con not process JUMPZ");
						popedJUMPZInstruction.setAttribute(InstructionCounter.getCurrentStatusOfInstructionount() + "");
						if (whileStack.isEmpty())
							throw new Exception(
									"Error while handling 'while stack' for while loop : can not find while in stack, stack is empty");
						Pair<Token, Instruction> whileStackPopWhilePair = whileStack.pop();
						Instruction popedWhileInstruction = whileStackPopWhilePair.getV();
						if (!"LABEL".equalsIgnoreCase(popedWhileInstruction.getInstruction()))
							throw new Exception(
									"Error while handling 'while stack' for while loop : can not find WHILE at top of stack, when stack is not empty");
						instructionJUMP.setAttribute(popedWhileInstruction.getInstructionNumber() + "");
						instructionTable.addInstrustion(instructionJUMP);
					} else
						throw new SemanticException("ERROR occured in processesing of while loop");

				}

			}
				break;
			default:
				break;
			}

		}
			break;
		case IDENTIFIER: {
			lexicalProcessor.moveOneTokenBack();
			StatementSemanticAnalyzer statmentAnalyzer = new StatementSemanticAnalyzer(symbolTable, instructionTable,
					lexicalProcessor);
			statmentAnalyzer.A();
			// instructionTable.show();
		}

			break;
		case OPERATOR: {
			addtoOutput(CommonUtils.getFormattedString(token));

		}
			break;
		case INTEGER: {
			addtoOutput(CommonUtils.getFormattedString(token));
			addtoOutput("Exception : invalid Integer value at line number = " + token.getLineNumber());
		}
			break;
		case BOOLEAN: {
			addtoOutput(CommonUtils.getFormattedString(token));
			addtoOutput("Exception : invalid boolean value at line number = " + token.getLineNumber());
		}

			break;
		case SEPARATOR: {
			if (Separators.SEMICOLON.getValue().equals(token.getLexeme())) {
				addtoOutput(CommonUtils.getFormattedString(token));
			}
		}

			break;
		case INVALID_TOKEN: {
			addtoOutput(CommonUtils.getFormattedString(token));
			addtoOutput("invalid token value at line number = " + token.getLineNumber());
		}
			break;
		default:
			break;
		}

	}

	private void addtoOutput(String formattedTokenString) {
		if (print)
			System.out.println(formattedTokenString);
	}

	private Token getNextToken() {
		return lexicalProcessor.getNextToken();
	}

	private void moveTokenBack(int stepsToGoBAck) {
		for (int i = 0; i < stepsToGoBAck; i++) {
			lexicalProcessor.moveOneTokenBack();
		}

	}

}
