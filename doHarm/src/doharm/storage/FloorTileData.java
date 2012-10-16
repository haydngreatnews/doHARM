package doharm.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import doharm.logic.world.tiles.TileType;

public class FloorTileData extends TileData{
	
	private String name;
	private TileType type; 
	private List<Integer> imageIDs;
	private int animationSpeed;
	
	
	public FloorTileData(String line) 
	{
		imageIDs = new ArrayList<Integer>();
		
		Scanner scan = new Scanner(line);
		name = scan.next();
		if (scan.hasNextInt())
			type = TileType.values()[scan.nextInt()];
		else
			type = TileType.values()[scan.next().charAt(0)-'a'];
		while (scan.hasNextInt())
		{
			int next = scan.nextInt();
			if (!scan.hasNextInt() && !imageIDs.isEmpty())
				animationSpeed = next;
			else
				imageIDs.add(next);
		}
	}


	public String getName() 
	{
		return name;
	}


	public TileType getType() 
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
	
	public int getAnimSpeed(){
		return animationSpeed;
	}
	
	
	
}
