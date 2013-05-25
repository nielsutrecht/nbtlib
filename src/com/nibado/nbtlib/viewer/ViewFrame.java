package com.nibado.nbtlib.viewer;


import java.io.*;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;


import com.nibado.nbtlib.*;


public class ViewFrame extends Composite  
{
	private Shell shell;
	private Tree tree;
	public ViewFrame(Shell shell) 
	{
		super(shell, SWT.BORDER);
		this.shell = shell;
	}

	private static final long serialVersionUID = 1L;

	public void init()
	{
		setSize(800, 600);
		initMenu();
		tree = new Tree(this, SWT.MULTI | SWT.BORDER);
		tree.setSize(640, 480);
		tree.setLocation(5,5);
	
	}
	
	private void initTree(Tag tag)
	{
		TreeItem root = new TreeItem(tree, SWT.NONE);
		root.setText("[Root]");	
		
		addTag(tag, root);
		
		root.setExpanded(true);
		if(root.getItemCount() > 0)
			root.getItem(0).setExpanded(true);

	}
	
	
	@SuppressWarnings("unchecked")
	private void addTag(Tag tag, TreeItem parent)
	{
		TreeItem item  = new TreeItem(parent, SWT.NONE);
		String name = tag.getName();
		if(name == null)
			name = "[..]";
		
		
		switch(tag.getType())
		{

			case Tag.TYPE_BYTE :
				item.setText(name + " B:"+ ((TagPrimitive<Byte>)tag).getPayload().toString());
				break;
			case Tag.TYPE_BYTE_ARRAY :
				item.setText(name + " B[]:"+ ((TagByteArray)tag).getPayload().length);
				break;		
			case Tag.TYPE_COMPOUND :
				item.setText(name +  " C[]:"+((TagCompound)tag).getTags().size() + "");
				break;				
			case Tag.TYPE_DOUBLE :
				item.setText(name + " D:"+ ((TagPrimitive<Double>)tag).getPayload().toString());
				break;	
			case Tag.TYPE_FLOAT :
				item.setText(name + " F:"+ ((TagPrimitive<Float>)tag).getPayload().toString());
				break;			
			case Tag.TYPE_INT :
				item.setText(name + " I:"+ ((TagPrimitive<Integer>)tag).getPayload().toString());
				break;
			case Tag.TYPE_INT_ARRAY :
				item.setText(name + " I[]:"+ ((TagIntArray)tag).getPayload().length);
				break;		
			case Tag.TYPE_LIST :
				item.setText(name +  " L[]:"+((TagList)tag).getTags().size() + "");
				break;		
			case Tag.TYPE_LONG :
				item.setText(name + " L:"+ ((TagPrimitive<Long>)tag).getPayload().toString());
				break;				
			case Tag.TYPE_SHORT :
				item.setText(name + " S:"+ ((TagPrimitive<Short>)tag).getPayload().toString());
				break;		
			case Tag.TYPE_STRING :
				item.setText(name + " S[]:"+ ((TagPrimitive<String>)tag).getPayload().toString());
				break;	
					
		}
		
		
		if(tag.getType() == Tag.TYPE_COMPOUND || tag.getType() == Tag.TYPE_LIST)
		{
			ArrayList<Tag> tags =  tag.getType() == Tag.TYPE_COMPOUND ?((TagCompound)tag).getTags() : ((TagList)tag).getTags();
			
			for(Tag t : tags)
			{
				addTag(t, item);
			}
		}
		


	}
	
	private void initMenu()
	{
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		MenuItem fileItem = new MenuItem(menu, SWT.CASCADE);
		fileItem.setText("File");
		
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(fileMenu);
		
		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText("Open...");
		
		openItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				 String[] filterExtensions = { "*.schematic" };
						 FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
						 fileDialog.setText("Load a schematic" );
						 fileDialog.setFilterPath(".");
						 fileDialog.setFilterExtensions(filterExtensions);
						 String selectedFile = fileDialog.open();

						 if(selectedFile != null)
							 loadNbt(new File(selectedFile));
			}
			});

	}
	
	private void loadNbt(File selectedFile)
	{
		System.out.println("Loading: " + selectedFile.getName());
		NbtReader reader = new NbtReader();
		try {
			Tag tag = reader.read(selectedFile, true);
			initTree(tag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
