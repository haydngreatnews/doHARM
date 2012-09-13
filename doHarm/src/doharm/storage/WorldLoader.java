package doharm.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WorldLoader 
{
	private int numTilesX;
	private int numTilesY;
	private TilesetLoader tilesetLoader;

	public WorldLoader(String worldDirectory) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File("res/worlds/"+worldDirectory+"/world.txt"));
		numTilesX = scanner.nextInt();
		numTilesY = scanner.nextInt();
		String tileset = scanner.next();
		tilesetLoader = new TilesetLoader(tileset);
	}

	public int getNumTilesX() 
	{
		return numTilesX;
	}

	public int getNumTilesY() 
	{
		return numTilesY;
	}

	public TilesetLoader getTileset() 
	{
		return tilesetLoader;
	}
}
