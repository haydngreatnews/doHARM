package doharm.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TileData 
{
	private String name;
	private int type; //0=wall 1=walkable 2=water 3=object?
	private List<Integer> imageIDs;
	
	
	public TileData(String line) 
	{
		imageIDs = new ArrayList<Integer>();
		
		Scanner scan = new Scanner(line);
		name = scan.next();
		type = scan.nextInt();
		while (scan.hasNext())
		{
			imageIDs.add(scan.nextInt());
		}
	}


	public String getName() 
	{
		return name;
	}


	public int getType() 
	{
		return type;
	}


	/*public List<Integer> getImageIDs() 
	{
		return Collections.unmodifiableList(imageIDs);
	}*/


	public int getNumImages() 
	{
		return imageIDs.size();
	}
	
	public int getImageID(int imageNumber)
	{
		return imageIDs.get(imageNumber);
	}


	public int getNumFramesPerImage() {
		return 160; //TODO!
	}
	
	
	
}
