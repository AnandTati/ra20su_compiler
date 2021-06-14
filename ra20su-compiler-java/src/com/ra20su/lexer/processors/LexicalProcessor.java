package com.ra20su.lexer.processors;

import java.util.ArrayList;
import java.util.List;

import com.ra20su.lexer.library.tokens.TokenName;
import com.ra20su.lexer.objects.Token;
import com.ra20su.utils.CommonUtils;
import com.ra20su.utils.Pair;
import com.ra20su.utils.TokenUtils;

public class LexicalProcessor {

	private List<Token> tokens = null;

	private int index = 0;

	private String inputFileAsString = "";

	private ArrayList<Token> tokensVisited = new ArrayList<>();

	public LexicalProcessor(String inputFileAsString) {
		super();
		this.index = 0;
		this.inputFileAsString = inputFileAsString;
		processLexicalAnalysis(this.inputFileAsString);
	}

	public LexicalProcessor() {
	}

	private void processLexicalAnalysis(String inputFileAsString) {
		this.tokens = new ArrayList<>();
		this.index = 0;

		List<Pair<StringBuilder, Integer>> ListOfLineAndLineNumber = processSeparatorsOperatorsAndQuatesWithSpaces(
				inputFileAsString);

		for (Pair<StringBuilder, Integer> lineAndLineNUmber : ListOfLineAndLineNumber) {
			StringBuilder line = lineAndLineNUmber.getK();
			int lineNumber = lineAndLineNUmber.getV();
			if (CommonUtils.isEmpty(line))
				continue;
			String[] stringTokens = line.toString().split("( )+");
			for (int i = 0; i < stringTokens.length; i++) {
				String lexeme = stringTokens[i].trim();
				if (CommonUtils.isEmpty(lexeme))
					continue;
				if (TokenUtils.isValidOperator(lexeme.trim()))
					tokens.add(new Token(TokenName.OPERATOR, lexeme, lineNumber));
				else if (TokenUtils.isValidSeparator(lexeme))
					tokens.add(new Token(TokenName.SEPARATOR, lexeme, lineNumber));
				else if (TokenUtils.isValidKeywaord(lexeme))
					tokens.add(new Token(TokenName.KEYWAORD, lexeme, lineNumber));
				else if (TokenUtils.isInteger(lexeme))
					tokens.add(new Token(TokenName.INTEGER, lexeme, lineNumber));
				else if (TokenUtils.isBoolean(lexeme))
					tokens.add(new Token(TokenName.BOOLEAN, lexeme, lineNumber));
				else if (TokenUtils.isValidIdentifier(lexeme))
					tokens.add(new Token(TokenName.IDENTIFIER, lexeme, lineNumber));
				else
					tokens.add(new Token(TokenName.INVALID_TOKEN, lexeme, lineNumber));
			}
		}
	}

	public Token lexer() {
		Token token = null;
		if (!CommonUtils.isEmpty(this.tokens)) {
			if (index < this.tokens.size())
				return this.tokens.get(index++);
		}

		return token;
	}

	private List<Pair<StringBuilder, Integer>> processSeparatorsOperatorsAndQuatesWithSpaces(String str) {

		String[] lines = str.split(System.lineSeparator());

		List<Pair<StringBuilder, Integer>> ListOfLineAndLineNumber = new ArrayList<>();
		boolean comment = false;
		int lineNumber = 0;
		for (String line : lines) {
			lineNumber++;
			StringBuilder stringBuilder = new StringBuilder();
			char[] charArray = line.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				// Consider variable assignment also
				char chr = charArray[i];

				if ('[' == chr) {
					if (charArray[i + 1] == '*')
						comment = true;
				} else if (']' == chr) {
					if (charArray[i - 1] == '*' && i < charArray.length) {
						comment = false;
						continue;
					}
				}
				if (!comment) {
					if (TokenUtils.isValidSeparator(chr))
						stringBuilder.append(" ").append(chr).append(" ");
					else if (TokenUtils.isValidOperator(chr + ""))
						i = processOperator(i, charArray, stringBuilder);
					else
						stringBuilder.append(chr);
				}
			}
			ListOfLineAndLineNumber.add(new Pair<StringBuilder, Integer>(stringBuilder, lineNumber));
		}

		return ListOfLineAndLineNumber;
	}

	private int processOperator(int i, char[] charArrayOfInputString, StringBuilder outputStringBuilder) {
		StringBuilder inputOperatorString = new StringBuilder();
		int index = i;
		for (int j = i; j < charArrayOfInputString.length; j++) {
			char chr = charArrayOfInputString[j];
			if (TokenUtils.isValidOperator(inputOperatorString.toString() + chr)) {
				inputOperatorString.append(chr);
				index = j;
			} else {
				if (TokenUtils.isValidOperator(chr)) {
					// TODO : throw exception
				} else if (TokenUtils.isValidSeparator(chr)) {
					// TODO : throw exception
				}
				break;
			}
		}
		outputStringBuilder.append(" ").append(inputOperatorString.toString()).append(" ");
		return index;
	}

	public LexicalProcessor refresh() {
		this.index = 0;
		return this;
	}

	public Token getNextToken() {
		Token token = lexer();
		tokensVisited.add(token);
		return token;
	}

	public Token getListVisitedToken() {
		if (!CommonUtils.isEmpty(tokensVisited)) {
			if (tokensVisited.size() > 1)
				return tokensVisited.get(tokensVisited.size() - 2);
		}

		return null;
	}

	public Token getlastVisitedTokenPerticular(int numberInReverse) {
		if (!CommonUtils.isEmpty(this.tokensVisited) && tokensVisited.size() >= numberInReverse) {
			return tokensVisited.get(tokensVisited.size() - numberInReverse);
		}

		return null;
	}

	public void moveOneTokenBack() {
		this.index--;
		this.tokensVisited.remove(tokensVisited.size() - 1);
	}

}
