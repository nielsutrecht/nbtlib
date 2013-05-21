package com.nibado.nbtlib;

public class TagByteArray  extends Tag
{
	private byte[] payload;
	
	public TagByteArray()
	{
		super(Tag.TYPE_BYTE_ARRAY);
	}
	
	public byte[] getPayload()
	{
		return payload;
	}
	
	public void setPayload(byte[] payload)
	{
		this.payload = payload;
	}
}
