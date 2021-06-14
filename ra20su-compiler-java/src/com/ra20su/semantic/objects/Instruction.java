package com.ra20su.semantic.objects;

public class Instruction {

	private int instructionNumber;
	private String instruction;
	private String attribute;

	public Instruction() {
		super();
		this.instructionNumber = InstructionCounter.getInstructionNumber();
	}

	public Instruction(String instruction, String attribute) {
		super();

		this.instruction = instruction;
		this.attribute = attribute;
		this.instructionNumber = InstructionCounter.getInstructionNumber();
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public int getInstructionNumber() {
		return instructionNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((instruction == null) ? 0 : instruction.hashCode());
		result = prime * result + instructionNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instruction other = (Instruction) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (instruction == null) {
			if (other.instruction != null)
				return false;
		} else if (!instruction.equals(other.instruction))
			return false;
		if (instructionNumber != other.instructionNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Instruction [instructionNumber=" + instructionNumber + ", instruction=" + instruction + ", attribute=" + attribute + "]";
	}

}
