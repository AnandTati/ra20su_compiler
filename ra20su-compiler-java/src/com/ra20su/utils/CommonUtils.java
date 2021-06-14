package com.ra20su.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.ra20su.lexer.objects.Token;
import com.ra20su.lexer.processors.LexicalProcessor;
import com.ra20su.semantic.InstructionTable;
import com.ra20su.semantic.SymbolTable;
import com.ra20su.semantic.objects.Instruction;
import com.ra20su.semantic.objects.Symbol;

public class CommonUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static boolean isEmpty(StringBuilder builder) {
		if (builder != null)
			return builder.toString().isEmpty();
		return true;
	}

	public static <T> boolean isEmpty(List<T> list) {
		return list == null || list.isEmpty();
	}

	public static void show(String key, String value) {

		System.out.println(String.format("%-20s %-20s \n", key, value));

	}

	public static String readFile(File file) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {

			int charInt = -1;
			while ((charInt = bufferedInputStream.read()) != -1) {
				stringBuilder.append((char) charInt);
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			throw e;
		}
	}

	public static File[] getFilesFromTheFolder(String directoryPath) throws Exception {
		File inputFOlder = new File(directoryPath);

		if (inputFOlder.isDirectory()) {

			return inputFOlder.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					if (pathname.getName().endsWith(".txt")) {
						return true;
					}
					return false;
				}
			});

		} else
			throw new Exception("The given file path is not a directory");
	}

	public static File writeToOutputFile(List<Token> listOfTokensAndLexems, String outputDirectory,
			String outputFileName) throws IOException {
		File outPutDirectory = new File(outputDirectory);
		if (!outPutDirectory.exists())
			outPutDirectory.mkdirs();
		File file = new File(outputDirectory + "/" + outputFileName);
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
			for (Token token : listOfTokensAndLexems) {
				String format = String.format("%-20s %-20s \n", token.getNameString(), token.getLexeme());
				wtriteLine(bufferedWriter, format);
			}
			return file;
		} catch (IOException e) {
			throw e;
		}
	}

	public static File writeToOutputFile(LexicalProcessor lexicalProcessor, String outputDirectory,
			String outputFileName) throws IOException {
		File outPutDirectory = new File(outputDirectory);
		if (!outPutDirectory.exists())
			outPutDirectory.mkdirs();
		File file = new File(outputDirectory + "/" + outputFileName);
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
			Token token = null;
			while ((token = lexicalProcessor.lexer()) != null) {
				// System.out.println(String.format("%-20s %-20s \n", token.getNameString(),
				// token.getLexeme()));
				String format = String.format("%-20s %-20s \n", token.getNameString(), token.getLexeme());
				wtriteLine(bufferedWriter, format);
			}
			return file;
		} catch (IOException e) {
			throw e;
		}
	}

	public static File writeListToOutputFile(List<String> list, String outputDirectory, String outputFileName)
			throws IOException {
		File outPutDirectory = new File(outputDirectory);
		if (!outPutDirectory.exists())
			outPutDirectory.mkdirs();
		File file = new File(outputDirectory + "/" + outputFileName);
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
			Token token = null;
			for (String string : list) {
				wtriteLine(bufferedWriter, string);
			}
			return file;
		} catch (IOException e) {
			throw e;
		}
	}

	public static String getFormattedString(Token token) {
		return String.format("%-20s %-20s", token.getNameString(), token.getLexeme());
	}

	public static File writeSemanticOutputFile(String outputDirectory, String outputFileName, SymbolTable symbolTable,
			InstructionTable instructionTable) throws IOException {

		File outPutDirectory = new File(outputDirectory);
		if (!outPutDirectory.exists())
			outPutDirectory.mkdirs();
		File file = new File(outputDirectory + "/" + outputFileName);
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
			String strHeading = String.format("%-20s %-20s %-20s", "", "Symbol Table", "");
			wtriteLine(bufferedWriter, strHeading);
			strHeading = String.format("%-20s %-20s %-20s", "Name", "Memory Location", "Type");
			wtriteLine(bufferedWriter, strHeading);
			wtriteLine(bufferedWriter,
					"--------------------------------------------------------------------------------------------");
			for (Symbol symbol : symbolTable.getListOfSymbols()) {
				String str = String.format("%-20s %-20s %-20s", symbol.getSymbolName(), symbol.getMemLocation(),
						symbol.getType());
				wtriteLine(bufferedWriter, str);
			}
			wtriteLine(bufferedWriter,
					"--------------------------------------------------------------------------------------------");
			wtriteLine(bufferedWriter, "\n\n\n");
			// instruction Table
			strHeading = String.format("%-20s %-20s %-20s", "", "Assembly Code", "");
			wtriteLine(bufferedWriter, strHeading);
			wtriteLine(bufferedWriter,
					"--------------------------------------------------------------------------------------------");
			for (Instruction instruction : instructionTable.getInstrucions()) {
				String attribute = instruction.getAttribute();
				if (CommonUtils.isEmpty(attribute)) {
					attribute = "";
				}
				String str = String.format("%-20s %-20s %-20s", instruction.getInstructionNumber(),
						instruction.getInstruction(), attribute);
				wtriteLine(bufferedWriter, str);
			}
			wtriteLine(bufferedWriter,
					"--------------------------------------------------------------------------------------------");

			return file;
		} catch (IOException e) {
			throw e;
		}
	}

	private static void wtriteLine(BufferedWriter bufferedWriter, String str) throws IOException {
		bufferedWriter.write(str);
		bufferedWriter.newLine();
		bufferedWriter.flush();
	}
}
