package doharm.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RampTileData extends TileData {
	private String name;
	private boolean walkable = true;//in-file, is 0 for walkable, 1 for not walkable
	private List<Integer> imageIDs;
	
	
	public RampTileData(String line) 
	{
		imageIDs = new ArrayList<Integer>();
		
		Scanner scan = new Scanner(line);
		name = scan.next();
		walkable = (scan.nextInt() == 0) ? true : false;
		while (scan.hasNext())
		{
			imageIDs.add(scan.nextInt());
		}
	}


	public String getName(){
		return name;
	}

	/*public List<Integer> getImageIDs() 
	{
		return Collections.unmodifiableList(imageIDs);
	}*/


	public int getNumImages(){
		return imageIDs.size();
	}
	
	public int getImageID(int imageNumber){
		return imageIDs.get(imageNumber);
	}
	
	public boolean isWalkable(){
		return walkable;
	}
}
