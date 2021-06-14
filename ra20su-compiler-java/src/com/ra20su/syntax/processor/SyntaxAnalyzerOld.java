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

public class SyntaxAnalyzerOld {

	private LexicalProcessor lexicalProcessor = null;

	private Stack<Token> stack = new Stack<>();

	private boolean print = true;

	private List<String> outputStrings = new ArrayList<>();

	public SyntaxAnalyzerOld(LexicalProcessor lexicalProcessor) {
		super();
		this.lexicalProcessor = lexicalProcessor;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public void process() throws SyntaxException {
		r1_Ra20SU();
	}

	public void r1_Ra20SU() throws SyntaxException {

		if ("$".equals(getNextToken().getLexeme())) {
			if ("$".equals(getNextToken().getLexeme())) {

				Token token = null;
				while ((token = getNextToken()) != null) {
					processToken(token);
					if ("$".equals(token.getLexeme())) {
						if ("$".equals(token.getLexeme())) {
							// TODO - take decision
						}
					}
				}

			}

		}
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
					Expresions expresions = new Expresions(lexicalProcessor, outputStrings);
					expresions.Conditions();

				} else {

					String message = getMessage("Exception : Expected " + TokenName.SEPARATOR.getValue() + " '"
							+ Separators.PARANTHESIS_OPENING.getValue() + "' after Keyword " + Keywords.IF.getValue(),
							token.getLineNumber());
					outputStrings.add(message);
					if (print)
						printError(message);
					else
						throw new SyntaxException(message);
				}

			}
				break;

			case "otherwise": {
				addtoOutput(CommonUtils.getFormattedString(token));
				if (stack.isEmpty())
					addtoOutput("Invalid keyword '" + Keywords.OTHERWISE.getValue() + "' at line number = "
							+ token.getLineNumber());
				else {
					Token tokenExpectedTobeIf = stack.lastElement();
					if (!"if".equals(tokenExpectedTobeIf.getLexeme()))
						addtoOutput("Invalid keyword '" + Keywords.OTHERWISE.getValue() + "' at line number = "
								+ token.getLineNumber());
				}
			}
				break;

			case "fi": {
				if (stack.isEmpty()) {
					addtoOutput("unexpected keyword fi at line number = " + token.getLineNumber());
				} else {
					Token tokenExpectedTobeIf = stack.lastElement();
					if ("if".equals(tokenExpectedTobeIf.getLexeme())) {
						stack.pop();
						addtoOutput(CommonUtils.getFormattedString(token));
					} else {
						addtoOutput("unexpected keyword fi at line number = " + token.getLineNumber()
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
							addtoOutput("Expected '" + Separators.PARANTHESIS_CLOSING.getValue()
									+ "' after an identifier in a " + Keywords.GET.getValue()
									+ " statement at line number  = " + nextToken.getLineNumber());
						}

					} else {
						addtoOutput("Expected an Identifier as an parameter in " + Keywords.GET.getValue()
								+ " statement at line number  = " + nextToken.getLineNumber());
					}
				} else {
					addtoOutput("Expected closing parathesis after keyward " + Keywords.GET.getValue()
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
							addtoOutput("Expected '" + Separators.PARANTHESIS_CLOSING.getValue()
									+ "' after an identifier in a " + Keywords.PUT.getValue()
									+ " statement at line number  = " + nextToken.getLineNumber());
						}

					} else {
						addtoOutput("Expected an Identifier as an parameter in " + Keywords.PUT.getValue()
								+ " statement at line number  = " + nextToken.getLineNumber());
					}
				} else {
					addtoOutput("Expected closing parathesis after keyward " + Keywords.PUT.getValue()
							+ " at line number = " + token.getLineNumber());
				}
			}
				break;
			case "int": {
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
							addtoOutput("Invalid separator at line = " + nextToken.getLineNumber());

					} else if ("=".equals(nextToken.getLexeme())) {
						addtoOutput("<Statement> -> <Assign>");
						addtoOutput("<Assign> ->  <Identifier>  = <Expression> ;");
						addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
						nextToken = getNextToken();
						if (TokenName.INTEGER.equals(nextToken.getName())) {
							addtoOutput("\n" + CommonUtils.getFormattedString(nextToken));
							addtoOutput("<Factor> -> <Identifier>");
						} else {
							addtoOutput("Expected an " + TokenName.INTEGER.getValue()
									+ " in assignment statement at line number = " + nextToken.getLineNumber());
						}
					} else {
						addtoOutput("Invalid token '" + nextToken.getLexeme() + "' at line number = "
								+ nextToken.getLineNumber());
					}
				} else
					addtoOutput("Expected an " + TokenName.IDENTIFIER.getValue()
							+ " after keywaord 'int' at line number = " + nextToken.getLineNumber());

			}
				break;
			case "while": {

				printIfRule(token);
				stack.push(token);
				Token nextToken = getNextToken();
				if (nextToken.getName().equals(TokenName.SEPARATOR)
						&& nextToken.getLexeme().equals(Separators.PARANTHESIS_OPENING.getValue())) {
					addtoOutput(CommonUtils.getFormattedString(getNextToken()));
					lexicalProcessor.moveOneTokenBack();
					Expresions expresions = new Expresions(lexicalProcessor, outputStrings);
					expresions.Conditions();

				} else {

					String message = getMessage("Exception : Expected " + TokenName.SEPARATOR.getValue() + " '"
							+ Separators.PARANTHESIS_OPENING.getValue() + "' after Keyword "
							+ Keywords.WHILE.getValue(), token.getLineNumber());
					outputStrings.add(message);
					if (print)
						printError(message);
					else
						throw new SyntaxException(message);
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
			Expresions expresions = new Expresions(lexicalProcessor, outputStrings);
			expresions.Expression();
		}

			break;
		case OPERATOR: {
			System.out.println(CommonUtils.getFormattedString(token));

		}
			break;
		case INTEGER:

			break;
		case BOOLEAN:

			break;
		case SEPARATOR: {
			if (Separators.SEMICOLON.getValue().equals(token.getLexeme())) {
				addtoOutput(CommonUtils.getFormattedString(token));
			}
		}

			break;
		case INVALID_TOKEN:

			break;
		default:
			break;
		}

	}

	private void addtoOutput(String formattedTokenString) {
		outputStrings.add(formattedTokenString);
		System.out.println(formattedTokenString);
	}

	private void printIfRule(Token token) {
		String formatIf = CommonUtils.getFormattedString(token);
		outputStrings.add(formatIf);
		System.out.println(formatIf);
		String ifRule = "<If> ::= if  ( <Condition>  ) <Statement> <If Prime>  fi";
		ifRule = ifRule + System.lineSeparator() + "<If Prime> ::= <Empty> | otherwise <Statement>";
		System.out.println(ifRule);
	}

	private void printError(String string) {
		System.out.println(string);
	}

	private String getMessage(String string, int lineNumber) {
		return string + " at line = " + lineNumber;
	}

	private Token getNextToken() {
		return lexicalProcessor.getNextToken();
	}

}
