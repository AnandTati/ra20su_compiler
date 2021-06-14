package com.ra20su.semantic.objects;

public class Symbol {

	private String symbolName;
	private int memLocation;
	private String type;

	public Symbol() {
		super();
		this.memLocation = MemoryLocationProvider.getMemLoaction();
	}

	public Symbol(String symbol, String type) {
		super();
		this.symbolName = symbol;
		this.memLocation = MemoryLocationProvider.getMemLoaction();
		this.type = type;
	}

	public String getSymbolName() {
		return symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMemLocation() {
		return memLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + memLocation;
		result = prime * result + ((symbolName == null) ? 0 : symbolName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Symbol other = (Symbol) obj;
		if (memLocation != other.memLocation)
			return false;
		if (symbolName == null) {
			if (other.symbolName != null)
				return false;
		} else if (!symbolName.equals(other.symbolName))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Symbol [symbolName=" + symbolName + ", memLocation=" + memLocation + ", type=" + type + "]";
	}

}
