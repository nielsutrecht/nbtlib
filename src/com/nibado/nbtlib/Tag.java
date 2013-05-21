package com.nibado.nbtlib;

public abstract class Tag 
{
	private int type;
	private String name;
	
	public Tag(int type)
	{
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}
	
	public String toString()
	{
		return "Tag:{" + NAMES[type] + ":'" + name + "'}";
	}
	
	
	public static final String[] NAMES = { "End", "Byte", "Short", "Int", "Long", "Float", "Double", "ByteArray", "String", "List", "Compound", "IntArray" };
	public static final int TYPE_END = 0, TYPE_BYTE = 1, TYPE_SHORT = 2, TYPE_INT = 3, TYPE_LONG = 4, TYPE_FLOAT = 5, TYPE_DOUBLE = 6, TYPE_BYTE_ARRAY = 7, TYPE_STRING = 8, TYPE_LIST = 9, TYPE_COMPOUND = 10, TYPE_INT_ARRAY = 11;
}
