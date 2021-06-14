package com.ra20su.semantic.objects;

public class InstructionCounter {

	private static int INSTRUCTION_NUMBER = 1;

	public static int getInstructionNumber() {
		return INSTRUCTION_NUMBER++;
	}

	public static void reset() {
		INSTRUCTION_NUMBER = 1;
	}

	public static int getCurrentStatusOfInstructionount() {
		return INSTRUCTION_NUMBER;
	}
}
