package com.ra20su.semantic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.ra20su.exceptions.SemanticException;
import com.ra20su.semantic.objects.Instruction;

public class InstructionTable {

	private TreeMap<Integer, Instruction> INSTRUCTION_NUMBER_VS_SYMBOLTABLE_MAP = new TreeMap<>();

	public InstructionTable() {
		super();
	}

	public void addInstrustion(String instruction, String attribute) throws SemanticException {

		Instruction instructionObj = new Instruction(instruction, attribute);
		if (INSTRUCTION_NUMBER_VS_SYMBOLTABLE_MAP.containsKey(instructionObj.getInstructionNumber()))
			throw new SemanticException(
					"The instruction number '" + instructionObj + "' already present in the instruction table");

		INSTRUCTION_NUMBER_VS_SYMBOLTABLE_MAP.put(instructionObj.getInstructionNumber(), instructionObj);
	}

	public void addInstrustion(Instruction instructionObj) throws SemanticException {

		if (INSTRUCTION_NUMBER_VS_SYMBOLTABLE_MAP.containsKey(instructionObj.getInstructionNumber()))
			throw new SemanticException(
					"The instruction number '" + instructionObj + "' already present in the instruction table");
		INSTRUCTION_NUMBER_VS_SYMBOLTABLE_MAP.put(instructionObj.getInstructionNumber(), instructionObj);
	}

	public Instruction getInstruction(int instructionNumber) throws SemanticException {

		if (INSTRUCTION_NUMBER_VS_SYMBOLTABLE_MAP.containsKey(instructionNumber))
			return INSTRUCTION_NUMBER_VS_SYMBOLTABLE_MAP.get(instructionNumber);
		throw new SemanticException(
				"The instruction number '" + instructionNumber + "' is not present in the instruction table");
	}

	public List<Instruction> getInstrucions() {
		List<Instruction> list = new ArrayList<>();
		for (Entry<Integer, Instruction> entry : INSTRUCTION_NUMBER_VS_SYMBOLTABLE_MAP.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

}
