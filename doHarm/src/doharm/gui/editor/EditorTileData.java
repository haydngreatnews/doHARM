package doharm.gui.editor;

import java.util.Scanner;

public class EditorTileData 
{
	private String name;
	private int type; //0=wall 1=walkable 2=water 3=object?
	
	
	public EditorTileData(String line) 
	{
		
		Scanner scan = new Scanner(line);
		name = scan.next();

	}


	public String getName() 
	{
		return name;
	}


	public int getType() 
	{
		return type;
	}

	
	
	
}