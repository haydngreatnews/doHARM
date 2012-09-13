package doharm.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WorldLoader 
{
	private int numTilesX;
	private int numTilesY;
	private String worldDirectory;
	private List<String> layerFiles;
	
	private TilesetLoader tilesetLoader;
	private LayerLoader layerLoader;

	public WorldLoader(String worldDirectory) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File("res/worlds/"+worldDirectory+"/world.txt"));
		numTilesX = scanner.nextInt();
		numTilesY = scanner.nextInt();
		String tileset = scanner.next();
		
		this.worldDirectory = worldDirectory;
		
		while (scanner.hasNext())
		{
			layerFiles.add(scanner.next());
		}
		
		tilesetLoader = new TilesetLoader(tileset);
		layerLoader = new LayerLoader(Collections.unmodifiableList(layerFiles));
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
	
	public String getWorldDirectory()
	{
		return worldDirectory;
	}

	public TilesetLoader getTilesetLoader() {
		return tilesetLoader;
	}

	public List<String> getLayerFiles() {
		return layerFiles;
	}
}
