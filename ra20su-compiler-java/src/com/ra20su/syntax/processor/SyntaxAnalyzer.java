package com.ra20su.syntax.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.ra20su.exceptions.SyntaxException;
import com.ra20su.lexer.library.tokens.Keywords;
import com.ra20su.lexer.library.tokens.Separators;
import com.ra20su.lexer.library.tokens.TokenName;
import com.ra20su.lexer.objects.Token;
import com.ra20su.lexer.processors.LexicalProcessor;
import com.ra20su.utils.CommonUtils;

public class SyntaxAnalyzer {

	private LexicalProcessor lexicalProcessor = null;

	private Stack<Token> stack = new Stack<>();

	private boolean print = true;

	private List<String> outputStrings = new ArrayList<>();

	public SyntaxAnalyzer(LexicalProcessor lexicalProcessor) {
		super();
		this.lexicalProcessor = lexicalProcessor;
	}

	public SyntaxAnalyzer(LexicalProcessor lexicalProcessor, boolean printOnScreen) {
		super();
		this.print = printOnScreen;
		this.lexicalProcessor = lexicalProcessor;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public List<String> process() throws SyntaxException {
		r1_Ra20SU();
		return outputStrings;
	}

	public void r1_Ra20SU() throws SyntaxException {

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

				if (!stack.isEmpty()) {
					String outputError = "";
					outputError = outputError + "Found unresolved closures for the following tokens \n";
					while (!stack.isEmpty()) {
						Token popedToken = stack.pop();
						outputError = outputError + "'" + popedToken.getLexeme() + "' at line number = "
								+ popedToken.getLineNumber() + "\n";

					}
					throw new SyntaxException(outputError);
				}

			}
		}
		if (!startOfFile)
			addtoOutput("\n EXCEPTION : Code must start with marker $$\"");
		if (!endOfFile)
			addtoOutput("\n EXCEPTION : Code must end with marker $$");
	}

	private void processToken(Token token) throws SyntaxException {
		addtoOutput(System.lineSeparator());
		switch (token.getName()) {
		case KEYWAORD: {
			String lexeme = token.getLexeme();
			switch (lexeme) {
			case "if": {
				printIfRule(token);
				stack.push(token);
				Token nextToken = getNextToken();
				if (nextToken.getName().equals(TokenName.SEPARATOR)
						&& nextToken.getLexeme().equals(Separators.PARANTHESIS_OPENING.getValue())) {
					addtoOutput(CommonUtils.getFormattedString(getNextToken()));
					lexicalProcessor.moveOneTokenBack();
					processCondition();

				} else {
					String message = getMessage("Exception : Expected " + TokenName.SEPARATOR.getValue() + " '"
							+ Separators.PARANTHESIS_OPENING.getValue() + "' after Keyword " + Keywords.IF.getValue(),
							token.getLineNumber());
					addtoOutput(message);
				}

			}
				break;

			case "otherwise": {
				addtoOutput(CommonUtils.getFormattedString(token));
				if (stack.isEmpty())
					addtoOutput("Exception : Invalid keyword '" + Keywords.OTHERWISE.getValue() + "' at line number = "
							+ token.getLineNumber());
				else {
					Token tokenExpectedTobeIf = stack.lastElement();
					if (!"if".equals(tokenExpectedTobeIf.getLexeme()))
						addtoOutput("Exception : Invalid keyword '" + Keywords.OTHERWISE.getValue()
								+ "' at line number = " + token.getLineNumber());
				}
			}
				break;

			case "fi": {
				if (stack.isEmpty()) {
					addtoOutput("Exception : unexpected keyword fi at line number = " + token.getLineNumber());
				} else {
					Token tokenExpectedTobeIf = stack.lastElement();
					if ("if".equals(tokenExpectedTobeIf.getLexeme())) {
						stack.pop();
						addtoOutput(CommonUtils.getFormattedString(token));
					} else {
						addtoOutput("Exception : unexpected keyword fi at line number = " + token.getLineNumber()
								+ ", as the corresponding 'if' keyward is not found");
					}
				}
			}

				break;
			case "get": {

				addtoOutput(CommonUtils.getFormattedString(token));
				addtoOutput("<Get> ::=    get ( <Identifier> );");
				Token nextToken = getNextToken();
				if (nextToken.getName().equals(TokenName.SEPARATOR)
						&& nextToken.getLexeme().equals(Separators.PARANTHESIS_OPENING.getValue())) {
					addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
					nextToken = getNextToken();
					if (TokenName.IDENTIFIER.equals(nextToken.getName())) {
						addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
						addtoOutput("<Factor> -> <Identifier>");
						nextToken = getNextToken();
						if (Separators.PARANTHESIS_CLOSING.getValue().equals(nextToken.getLexeme())) {
							addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
						} else {
							addtoOutput("Exception : Expected '" + Separators.PARANTHESIS_CLOSING.getValue()
									+ "' after an identifier in a " + Keywords.GET.getValue()
									+ " statement at line number  = " + nextToken.getLineNumber());
						}

					} else {
						addtoOutput("Exception : Expected an Identifier as an parameter in " + Keywords.GET.getValue()
								+ " statement at line number  = " + nextToken.getLineNumber());
					}
				} else {
					addtoOutput("Exception : Expected closing parathesis after keyward " + Keywords.GET.getValue()
							+ " at line number = " + token.getLineNumber());
				}
			}
				break;
			case "put": {
				addtoOutput(CommonUtils.getFormattedString(token));
				addtoOutput("<Put> ::=     put ( <identifier> );");
				Token nextToken = getNextToken();
				if (nextToken.getName().equals(TokenName.SEPARATOR)
						&& nextToken.getLexeme().equals(Separators.PARANTHESIS_OPENING.getValue())) {
					addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
					nextToken = getNextToken();
					if (TokenName.IDENTIFIER.equals(nextToken.getName())) {
						addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
						addtoOutput("<Factor> -> <Identifier>");
						nextToken = getNextToken();
						if (Separators.PARANTHESIS_CLOSING.getValue().equals(nextToken.getLexeme())) {
							addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
						} else {
							addtoOutput("Exception : Expected '" + Separators.PARANTHESIS_CLOSING.getValue()
									+ "' after an identifier in a " + Keywords.PUT.getValue()
									+ " statement at line number  = " + nextToken.getLineNumber());
						}

					} else {
						addtoOutput("Exception : Expected an Identifier as an parameter in " + Keywords.PUT.getValue()
								+ " statement at line number  = " + nextToken.getLineNumber());
					}
				} else {
					addtoOutput("Exception : Expected closing parathesis after keyward " + Keywords.PUT.getValue()
							+ " at line number = " + token.getLineNumber());
				}
			}
				break;
			case "integer": {
				addtoOutput(CommonUtils.getFormattedString(token));
				Token nextToken = getNextToken();
				if (TokenName.IDENTIFIER.equals(nextToken.getName())) {
					addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
					nextToken = getNextToken();
					if (TokenName.SEPARATOR.equals(nextToken.getName())) {
						if (";".equals(nextToken.getLexeme())) {
							addtoOutput("<Factor> -> <Identifier>");
							addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
						} else
							addtoOutput("Exception : Invalid separator at line = " + nextToken.getLineNumber());

					} else if ("=".equals(nextToken.getLexeme())) {
						addtoOutput("<Statement> -> <Assign>");
						addtoOutput("<Assign> ->  <Identifier>  = <Expression> ;");
						addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
						nextToken = getNextToken();
						if (TokenName.INTEGER.equals(nextToken.getName())) {
							addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
							addtoOutput("<Factor> -> <Identifier>");
						} else {
							addtoOutput("Exception : Expected an " + TokenName.INTEGER.getValue()
									+ " in assignment statement at line number = " + nextToken.getLineNumber());
						}
					} else {
						addtoOutput("Exception : Invalid token '" + nextToken.getLexeme() + "' at line number = "
								+ nextToken.getLineNumber());
					}
				} else
					addtoOutput("Exception : Expected an " + TokenName.IDENTIFIER.getValue()
							+ " after keywaord 'int' at line number = " + nextToken.getLineNumber());

			}
				break;
			case "while": {

				printWhileRule(token);
				Token nextToken = getNextToken();
				if (nextToken.getName().equals(TokenName.SEPARATOR)
						&& nextToken.getLexeme().equals(Separators.PARANTHESIS_OPENING.getValue())) {
					addtoOutput(CommonUtils.getFormattedString(getNextToken()));
					lexicalProcessor.moveOneTokenBack();
					processCondition();

				} else {

					String message = getMessage("Exception : Expected " + TokenName.SEPARATOR.getValue() + " '"
							+ Separators.PARANTHESIS_OPENING.getValue() + "' after Keyword "
							+ Keywords.WHILE.getValue(), token.getLineNumber());
					addtoOutput(message);

				}
			}
				break;
			default:
				break;
			}

		}
			break;
		case IDENTIFIER: {
			addtoOutput(CommonUtils.getFormattedString(token));
			lexicalProcessor.moveOneTokenBack();
			processExpression();
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

	private void processExpression() {
		Expresions expresions = new Expresions(lexicalProcessor, outputStrings);
		expresions.Expression();
		expresions.getOutPutStringList(print);
	}

	private void processCondition() {
		Expresions expresions = new Expresions(lexicalProcessor, outputStrings);
		expresions.Conditions();
		expresions.getOutPutStringList(print);
	}

	private void addtoOutput(String formattedTokenString) {
		outputStrings.add(formattedTokenString);
		if (print)
			System.out.println(formattedTokenString);
	}

	private void printIfRule(Token token) {
		String formatIf = CommonUtils.getFormattedString(token);
		addtoOutput(formatIf);
		addtoOutput("<If> ::=     if  ( <Condition>  ) <Statement>   fi   | ");
		addtoOutput("if  ( <Condition>  ) <Statement>   otherwise  <Statement>  fi " + "\n");
	}

	private void printWhileRule(Token token) {
		String formatIf = CommonUtils.getFormattedString(token);
		addtoOutput(formatIf);
		addtoOutput("<While> ::=  while ( <Condition>  )  <Statement> " + "\n");
	}

	private String getMessage(String string, int lineNumber) {
		return string + " at line = " + lineNumber;
	}

	private Token getNextToken() {
		return lexicalProcessor.getNextToken();
	}

}
