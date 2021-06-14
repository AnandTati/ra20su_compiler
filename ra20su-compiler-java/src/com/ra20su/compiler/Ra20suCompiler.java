package com.ra20su.compiler;

import java.io.File;
import java.util.List;

import com.ra20su.lexer.processors.LexicalProcessor;
import com.ra20su.semantic.processor.SemanticAnalyzer;
import com.ra20su.syntax.processor.SyntaxAnalyzer;
import com.ra20su.utils.CommonUtils;

public class Ra20suCompiler {
	private String inputDirectoryPath = "./InputFiles";

	public static void main(String[] args) throws Exception {
		System.out.println("\n\n------------------RxCompiler---------------------------------------\n\n");
		System.out.println("Note : You can add your input file into 'InputFiles' folder, the corresponding output file with name = <input file name>_output.txt can be seen in 'OutPuts' folder");
		Ra20suCompiler assignment2 = new Ra20suCompiler();
		assignment2.run();
	}

	public void run() throws Exception {
		processFileInputs("./Outputs");
	}

	private void processFileInputs(String outputDirectoryPath) throws Exception {

		File[] iputFiles = CommonUtils.getFilesFromTheFolder(this.inputDirectoryPath);
		System.out.println("\n\n\n");
		CommonUtils.show("Input File", "Output File");
		System.out.println("----------------------------------------------------------");
		String systemPathForOutputDirectoryPath = null;
		String systemPathForInputDirectoryPath = null;
		for (File inputFile : iputFiles) {
			try {
				String fileContent = CommonUtils.readFile(inputFile);
				LexicalProcessor lexicalProcessor = new LexicalProcessor(fileContent);
				String inputFileName = inputFile.getName().substring(0, inputFile.getName().lastIndexOf(".txt"));
				String outputFileName = inputFileName + "_output.txt";
				SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalProcessor, false);
				syntaxAnalyzer.process();
				SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(lexicalProcessor, false);
				semanticAnalyzer.process();
				File outputFile = CommonUtils.writeSemanticOutputFile(outputDirectoryPath, outputFileName, semanticAnalyzer.getSymbolTable(), semanticAnalyzer.getInstructionTable());
				if (systemPathForOutputDirectoryPath == null && outputFile != null) {
					systemPathForOutputDirectoryPath = outputFile.getParentFile().getCanonicalPath();
					systemPathForInputDirectoryPath = inputFile.getParentFile().getCanonicalPath();
				}
				CommonUtils.show(inputFile.getName(), outputFile.getName());
			} catch (Exception e) {
				System.out.println("ERROR: in file " + inputFile.getCanonicalPath());
				throw e;
			}
		}
		System.out.println("----------------------------------------------------------");
		System.out.println("All input Files are in directory  = " + systemPathForInputDirectoryPath);
		System.out.println("All output Files are in directory = " + systemPathForOutputDirectoryPath);
	}
}
