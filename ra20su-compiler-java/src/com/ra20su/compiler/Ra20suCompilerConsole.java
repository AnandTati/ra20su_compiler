package com.ra20su.compiler;

import com.ra20su.lexer.processors.LexicalProcessor;
import com.ra20su.semantic.processor.SemanticAnalyzer;
import com.ra20su.syntax.processor.SyntaxAnalyzer;
import com.ra20su.utils.CommonUtils;

public class Ra20suCompilerConsole {

	public static void main(String[] args) throws Exception {
		System.out.println("\n\n------------------RxCompiler---------------------------------------");
		System.out.println("please end code with \"eof\" \n>>");
		Ra20suCompilerConsole assignment2 = new Ra20suCompilerConsole();
		assignment2.run();

	}

	public void run() throws Exception {
		processConsoleInputs();
	}

	private void processConsoleInputs() throws Exception {
		String consoleContent = CommonUtils.readConsole();
		LexicalProcessor lexicalProcessor = new LexicalProcessor(consoleContent);
		SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalProcessor, false);
		syntaxAnalyzer.process();
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(lexicalProcessor, false);
		semanticAnalyzer.process();
		CommonUtils.writeSemanticOutputOnConsole(semanticAnalyzer.getSymbolTable(),
				semanticAnalyzer.getInstructionTable());
	}
}
