package com.nibado.nbtlib;

import java.util.ArrayList;

public class TagList  extends Tag
{
	private ArrayList<Tag> tags;
	private int listType;
	
	public TagList()
	{
		super(Tag.TYPE_LIST);
		tags = new ArrayList<Tag>();
	}
	
	public ArrayList<Tag> getTags()
	{
		return tags;
	}

	public int getListType() {
		return listType;
	}

	public void setListType(int listType) {
		this.listType = listType;
	}
}
