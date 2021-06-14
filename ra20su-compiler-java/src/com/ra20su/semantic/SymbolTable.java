package com.ra20su.semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.ra20su.exceptions.SemanticException;
import com.ra20su.semantic.objects.Symbol;
import com.ra20su.utils.CommonUtils;

public class SymbolTable {

	private HashMap<String, Symbol> SYMBOL_NAME_VS_SYMBOLTABLE_MAP = new HashMap<>();

	public void addSymbol(String symbol, String type) throws SemanticException {
		if (this.contains(symbol))
			throw new SemanticException("The Symbol already '" + symbol + "'present in the sumbol table");
		SYMBOL_NAME_VS_SYMBOLTABLE_MAP.put(symbol, new Symbol(type, type));
	}

	public void addSymbol(Symbol symbol) throws SemanticException {
		if (this.contains(symbol.getSymbolName()))
			throw new SemanticException("The Symbol already '" + symbol + "'present in the sumbol table");
		if (CommonUtils.isEmpty(symbol.getSymbolName()))
			throw new SemanticException("The Symbol name can not be empty");
		if (CommonUtils.isEmpty(symbol.getType()))
			throw new SemanticException("The Symbol Type can not be empty");
		SYMBOL_NAME_VS_SYMBOLTABLE_MAP.put(symbol.getSymbolName(), symbol);
	}

	public Symbol getSymbol(String symbol) throws SemanticException {

		if (this.contains(symbol))
			return SYMBOL_NAME_VS_SYMBOLTABLE_MAP.get(symbol);
		throw new SemanticException("Symbol Table does not have symbol '" + symbol + "'");
	}

	public boolean contains(String symbol) throws SemanticException {

		return SYMBOL_NAME_VS_SYMBOLTABLE_MAP.containsKey(symbol);

	}

	public List<Symbol> getListOfSymbols() {

		List<Symbol> list = new ArrayList<Symbol>();
		for (Entry<String, Symbol> entry : SYMBOL_NAME_VS_SYMBOLTABLE_MAP.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

}
