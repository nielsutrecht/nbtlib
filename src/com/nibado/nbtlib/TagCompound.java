package com.nibado.nbtlib;

import java.util.ArrayList;

public class TagCompound extends Tag {
	
	private ArrayList<Tag> tags;
	
	public TagCompound()
	{
		super(Tag.TYPE_COMPOUND);
		tags = new ArrayList<Tag>();
	}
	
	public ArrayList<Tag> getTags()
	{
		return tags;
	}
}
