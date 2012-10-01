package doharm.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TilesetLoader 
{
	private int floorTileWidth;
	private int floorTileHeight;
	
	private int wallTileWidth;
	private int wallTileHeight;
	
	private String floorTileSetImage;
	private String wallTileSetImage;
	
	private List<FloorTileData> floorTileData;
	private List<WallTileData> wallTileData;
	
	
	public TilesetLoader(String filename) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File("res/tilesets/"+filename));
		
		loadFloorTiles(scanner.nextLine().trim());
		loadWallTiles(scanner.nextLine().trim());
		

	}
	
	private void loadFloorTiles(String fname) throws FileNotFoundException{
		Scanner scanner = new Scanner(new File("res/tilesets/"+fname));
		floorTileSetImage = scanner.nextLine().trim();
		System.out.println("Floor tile set image fname: " + floorTileSetImage);
		
		floorTileWidth = Integer.parseInt(scanner.nextLine());
		floorTileHeight = Integer.parseInt(scanner.nextLine());
		
		floorTileData = new ArrayList<FloorTileData>();
		
		while(scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if (line.trim().length() > 0)
				floorTileData.add(new FloorTileData(line));
		}
	}
	
	private void loadWallTiles(String fname) throws FileNotFoundException{
		
		Scanner scanner = new Scanner(new File("res/tilesets/"+fname));
		wallTileSetImage = scanner.nextLine().trim();
		System.out.println("Wall tile set image fname: " + wallTileSetImage);
		
		wallTileWidth = Integer.parseInt(scanner.nextLine());
		wallTileHeight = Integer.parseInt(scanner.nextLine());
		
		wallTileData = new ArrayList<WallTileData>();
		
		while(scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if (line.trim().length() > 0)
				wallTileData.add(new WallTileData(line));
		}
		
	}
	
	
	
	
	public List<FloorTileData> getFloorTileData()
	{
		return Collections.unmodifiableList(floorTileData);
	}
	public List<WallTileData> getWallTileData()
	{
		return Collections.unmodifiableList(wallTileData);
	}

	
	

	
	public int getFloorTileWidth() {
		return floorTileWidth;
	}

	public int getFloorTileHeight() {
		return floorTileHeight;
	}

	public int getWallTileWidth() {
		return wallTileWidth;
	}

	public int getWallTileHeight() {
		return wallTileHeight;
	}

	public String getFloorTileSetImage() {
		return floorTileSetImage;
	}

	public String getWallTileSetImage() {
		return wallTileSetImage;
	}
	
	public FloorTileData getFloorTileData(int tileID) 
	{
		return floorTileData.get(tileID);
	}
	
	public WallTileData getWallTileData(int tileID) 
	{
		return wallTileData.get(tileID);
	}
	
	public int getNumFloorTiles(){
		return floorTileData.size();
	}
	
	public int getNumWallTiles(){
		return wallTileData.size();
	}
	
	
}
