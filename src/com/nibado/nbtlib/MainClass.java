package com.nibado.nbtlib;

import java.io.File;
import java.io.IOException;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		try {
			new NbtReader().read(new File("the-tavern.schematic"), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
