package com.nibado.nbtlib;

public class TagIntArray  extends Tag
{
	private int[] payload;
	
	public TagIntArray()
	{
		super(Tag.TYPE_BYTE_ARRAY);
	}
	
	public int[] getPayload()
	{
		return payload;
	}
	
	public void setPayload(int[] payload)
	{
		this.payload = payload;
	}
}
