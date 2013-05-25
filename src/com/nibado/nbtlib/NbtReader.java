package com.nibado.nbtlib;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class NbtReader 
{
	public Tag read(File nbtFile, boolean compressed) throws IOException
	{	
		FileInputStream ins = new FileInputStream(nbtFile);
		return read(ins, compressed);
	}
	
	public Tag read(InputStream ins, boolean compressed)  throws IOException
	{
		if(compressed)
		{
			ins = new GZIPInputStream(ins);
		}
		
		DataInputStream dataIn = new DataInputStream(ins);
		
		return read(dataIn);
	}
	
	private Tag read(DataInputStream dataIn) throws IOException
	{
		
		int type = dataIn.readByte();
		String name = dataIn.readUTF();
		
		if(type != 10)
			throw new IOException("File does not seem to be a NBT file, should start with byte value '10'");
		
		TagCompound rootTag = new TagCompound();
		rootTag.setName(name);
		
		System.out.println("Tag:{" + Tag.NAMES[type] + ":'" + name + "'}");
		
		readTags(dataIn, rootTag);
		
		dataIn.close();
		
		return rootTag;
	}
	
	@SuppressWarnings("unchecked")
	private void readTags(DataInputStream dataIn, TagCompound parent)  throws IOException
	{
		while(true)
		{
			Tag tag = null;
			int type = dataIn.readByte();
			String name = null;
			
			if(type != Tag.TYPE_END)
			{
				name = dataIn.readUTF();
				//System.out.println("Tag:{" + Tag.NAMES[type] + ":'" + name + "'}");
			}
			else
			{
				//System.out.println("Tag:{" + Tag.NAMES[type] + "}");
				break;
			}
			
			switch(type)
			{
				case Tag.TYPE_BYTE :
				{
					tag = new TagPrimitive<Byte>(type);
					((TagPrimitive<Byte>)tag).setPayload(new Byte(dataIn.readByte()));
					break;
				}
				case Tag.TYPE_BYTE_ARRAY :
				{
					int length = dataIn.readInt();
					byte[] payload = new byte[length];
					dataIn.readFully(payload, 0, length);
					tag = new TagByteArray();
					((TagByteArray)tag).setPayload(payload);
					break;
				}
				case Tag.TYPE_COMPOUND :
				{
					tag = new TagCompound();
					readTags(dataIn, (TagCompound)tag);
					break;
				}
				case Tag.TYPE_DOUBLE :
				{
					tag = new TagPrimitive<Double>(type);
					((TagPrimitive<Double>)tag).setPayload(new Double(dataIn.readDouble()));	
					break;
				}
				case Tag.TYPE_FLOAT :
				{
					tag = new TagPrimitive<Float>(type);
					((TagPrimitive<Float>)tag).setPayload(new Float(dataIn.readFloat()));		
					break;
				}				
				case Tag.TYPE_INT :
				{
					tag = new TagPrimitive<Integer>(type);
					((TagPrimitive<Integer>)tag).setPayload(new Integer(dataIn.readInt()));	
					break;
				}
				case Tag.TYPE_INT_ARRAY :
				{
					int length = dataIn.readInt();
					int[] payload = new int[length];
					for(int i = 0;i < length;i++)
					{
						payload[i] = dataIn.readInt();
					}
					tag = new TagIntArray();
					((TagIntArray)tag).setPayload(payload);
					break;
				}				
				case Tag.TYPE_LIST :
				{
					int listType = dataIn.readByte();
					int length = dataIn.readInt();
					
					tag = new TagList();
					((TagList)tag).setListType(listType);
					readList(dataIn, (TagList)tag, length);
					break;
				}
				case Tag.TYPE_LONG :
				{
					tag = new TagPrimitive<Long>(type);
					((TagPrimitive<Long>)tag).setPayload(new Long(dataIn.readLong()));
					break;
				}
				case Tag.TYPE_SHORT :
				{
					tag = new TagPrimitive<Short>(type);
					((TagPrimitive<Short>)tag).setPayload(new Short(dataIn.readShort()));
					break;
				}
				case Tag.TYPE_STRING :
				{
					tag = new TagPrimitive<String>(type);
					((TagPrimitive<String>)tag).setPayload(dataIn.readUTF());	
					break;
				}				
				default :
				{
					throw new IOException("Error reading tag, unknown tagtype: " + type);
				}
				
			}

			tag.setName(name);
			parent.getTags().add(tag);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void readList(DataInputStream dataIn, TagList parent, int length)   throws IOException
	{
		for(int i = 0;i < length;i++)
		{
			Tag tag = null;
			int type = parent.getListType();
			
			switch(type)
			{
				case Tag.TYPE_BYTE :
				{
					tag = new TagPrimitive<Byte>(type);
					((TagPrimitive<Byte>)tag).setPayload(new Byte(dataIn.readByte()));
					break;
				}
				case Tag.TYPE_BYTE_ARRAY :
				{
					int len = dataIn.readInt();
					byte[] payload = new byte[len];
					dataIn.readFully(payload, 0, len);
					tag = new TagByteArray();
					((TagByteArray)tag).setPayload(payload);
					break;
				}
				case Tag.TYPE_COMPOUND :
				{
					tag = new TagCompound();
					readTags(dataIn, (TagCompound)tag);
					break;
				}
				case Tag.TYPE_DOUBLE :
				{
					tag = new TagPrimitive<Double>(type);
					((TagPrimitive<Double>)tag).setPayload(new Double(dataIn.readDouble()));		
					break;
				}
				case Tag.TYPE_END :
				{
					break;
				}
				case Tag.TYPE_FLOAT :
				{
					tag = new TagPrimitive<Float>(type);
					((TagPrimitive<Float>)tag).setPayload(new Float(dataIn.readFloat()));	
					break;
				}				
				case Tag.TYPE_INT :
				{
					tag = new TagPrimitive<Integer>(type);
					((TagPrimitive<Integer>)tag).setPayload(new Integer(dataIn.readInt()));	
					break;
				}
				case Tag.TYPE_INT_ARRAY :
				{
					int len = dataIn.readInt();
					int[] payload = new int[len];
					for(int index = 0;index < len;index++)
					{
						payload[index] = dataIn.readInt();
					}
					tag = new TagIntArray();
					((TagIntArray)tag).setPayload(payload);
					break;
				}					
				case Tag.TYPE_LIST :
				{
					int listType = dataIn.readByte();
					int len = dataIn.readInt();
					
					tag = new TagList();
					((TagList)tag).setListType(listType);
					readList(dataIn, (TagList)tag, len);
					break;
				}
				case Tag.TYPE_LONG :
				{
					tag = new TagPrimitive<Long>(type);
					((TagPrimitive<Long>)tag).setPayload(new Long(dataIn.readLong()));		
					break;
				}
				case Tag.TYPE_SHORT :
				{
					tag = new TagPrimitive<Short>(type);
					((TagPrimitive<Short>)tag).setPayload(new Short(dataIn.readShort()));
					break;
				}
				case Tag.TYPE_STRING :
				{
					tag = new TagPrimitive<String>(type);
					((TagPrimitive<String>)tag).setPayload(dataIn.readUTF());	
					break;
				}				
				default :
				{
					throw new IOException("Error reading tag, unknown tagtype: " + type);
				}			
			}
			if(tag != null)
				parent.getTags().add(tag);
		}
	}
}
