package doharm.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WorldLoader 
{
	private int numTilesX;
	private int numTilesY;
	private String worldDirectory;
	
	private TilesetLoader tilesetLoader;
	private List<LayerData> layers;

	public WorldLoader(String worldDirectory) throws IOException
	{
		Scanner scanner = new Scanner(new File("res/worlds/"+worldDirectory+"/world.txt"));
		numTilesX = scanner.nextInt();
		numTilesY = scanner.nextInt();
		String tileset = scanner.next();
		
		this.worldDirectory = worldDirectory;
		layers = new ArrayList<LayerData>();
		while (scanner.hasNext())
		{
			layers.add(new LayerData(this, scanner.next()));
		}
		
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
	
	public String getWorldDirectory()
	{
		return worldDirectory;
	}

	public TilesetLoader getTilesetLoader() 
	{
		return tilesetLoader;
	}
	
	public LayerData getLayerData(int layerNumber)
	{
		return layers.get(layerNumber);
	}

	public int getNumLayers() 
	{
		return layers.size();
	}
}
