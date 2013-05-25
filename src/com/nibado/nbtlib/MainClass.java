package com.nibado.nbtlib;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.nibado.nbtlib.viewer.ViewFrame;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(800, 600);
		ViewFrame frame = new ViewFrame(shell);
		frame.init();
		shell.open();
		
		while(!shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
