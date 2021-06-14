package com.ra20su.syntax.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ra20su.lexer.library.tokens.TokenName;

public class symbol_Table {

	boolean print = true;
	private String outputString = "";
	private List<String> outputStrings = null;
	private Map<String, Integer> type_identifer = new HashMap<String, Integer>() {
		{
			put("Integer", 1);
			put("int", 2);
			put("object", 3);
			put("String", 1);
			put("boolean", 2);
			put("array", 3);
			put("Map", 3);
			put("list", 3);
		}
	};

	private Map<Integer, List<String>> symbol_list = new HashMap<>();

	/*
	 * public symbol_Table(LexicalProcessor lexicalProcessor, List<String>
	 * outputStrings) { super(); if (CommonUtils.isEmpty(outputStrings)) {
	 * outputStrings = new ArrayList<>(); } else this.outputStrings = outputStrings;
	 * this.lexicalProcessor = lexicalProcessor; token =
	 * lexicalProcessor.getNextToken(); }
	 */
	public List<String> getOutPutStringList(boolean printOnScreen) {
		addtoOutput(outputString, printOnScreen);
		return outputStrings;
	}

	public symbol_Table initialize(String[] args) {
		// TODO Auto-generated method stub
		symbol_Table st = new symbol_Table();
		System.out.println(st.add_Symbol("abc", "Integer"));
		System.out.println(st.add_Symbol("abc", "Integer"));
		System.out.println(st.add_Symbol("abc", "Intege"));
		System.out.println(st.add_Symbol("cdf", "Integer"));
		System.out.println(st.add_Symbol("cdf", "Int"));
		System.out.println(st.add_Symbol("abc", "int"));
		System.out.println(st.add_Symbol("xyz", "Integer"));
		st.print_table();
		return st;
	}

	public int add_Symbol(String identifier, String identifer_type) {

		int i = 5000;
		if (!type_identifer.containsKey(identifer_type)) {
			System.out.print("Incorrect Identifer Type ");
			return -1;
		}
		if (!check_symbol(identifier)) {
			while (symbol_list.containsKey(i))
				i++;
			List<String> AL = new ArrayList<>(Arrays.asList(identifier, identifer_type));
			symbol_list.put(i, AL);
			System.out.println(symbol_list);
			return i;
		}
		System.out.print("Identifer already present");
		return -1;
	}

	public boolean check_symbol(String identifier) {
		int i = 5000;
		List<String> tmp;
		// System.out.println(symbol_list);
		while (symbol_list.containsKey(i)) {
			tmp = symbol_list.get(i);
			// System.out.println(tmp.get(0));
			if (tmp.get(0).equals(identifier))
				return true;
			i++;
		}
		return false;

	}

	public String get_memoryLocation(String identifier) {
		int i = 5000;
		List<String> tmp;
		while (symbol_list.containsKey(i)) {
			tmp = symbol_list.get(i);
			// System.out.println(tmp.get(0));
			if (tmp.get(0).equals(identifier))
				return Integer.toString(i);
			i++;
		}
		return "null";
	}

	public void print_table() {
		int i = 5000;
		List<String> tmp;
		System.out.println("Symbol Table \n");
		System.out.println("Identifer  Address  Type");
		while (symbol_list.containsKey(i)) {
			tmp = symbol_list.get(i);
			System.out.println(tmp.get(0) + "   " + i + "   " + tmp.get(1));
			i++;
		}
	}

	private void addtoOutput(String formattedTokenString, boolean printOnScreen) {
		outputStrings.add(formattedTokenString);
		if (printOnScreen)
			System.out.println(formattedTokenString);
	}
}
