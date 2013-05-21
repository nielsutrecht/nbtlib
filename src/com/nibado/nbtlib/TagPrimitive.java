package com.nibado.nbtlib;

public class TagPrimitive<P>  extends Tag
{
	private P payload;
	public TagPrimitive(int type)
	{
		super(type);
	}
	
	public P getPayload()
	{
		return payload;
	}
	
	public void setPayload(P payload)
	{
		this.payload = payload;
	}
}
