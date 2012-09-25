package doharm.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TilesetLoader 
{
	private int tileWidth;
	private int tileHeight;
	private String tileSetImage;
	private List<TileData> tileData;

	public TilesetLoader(String filename) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File("res/tilesets/"+filename));
		tileWidth = Integer.parseInt(scanner.nextLine());
		tileHeight = Integer.parseInt(scanner.nextLine());
		int isowidth = Integer.parseInt(scanner.nextLine());
		int isoheight = Integer.parseInt(scanner.nextLine());
		
		tileSetImage = scanner.nextLine().trim();
		
		tileData = new ArrayList<TileData>();
		
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if (line.trim().length() > 0)
				tileData.add(new TileData(line));
		}
	}
	
	private void loadFloorTiles(){
		
	}
	
	
	/*public List<TileData> getTileData()
	{
		return Collections.unmodifiableList(tileData);
	}*/


	public int getTileWidth() {
		return tileWidth;
	}


	public int getTileHeight() {
		return tileHeight;
	}


	public String getTileSetImage() {
		return tileSetImage;
	}


	public TileData getTileData(int tileID) 
	{
		return tileData.get(tileID);
	}


	public int getNumTiles() 
	{
		return tileData.size();
	}
	
	
}
