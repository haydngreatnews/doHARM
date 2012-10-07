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
		
		boolean generate = false;
		
		
		numTilesX = scanner.nextInt();
		numTilesY = scanner.nextInt();
		String tileset = scanner.next();
		
		
		if (generate)
		{
			numTilesX = 20;
			numTilesY = 20;
			
			
		}
		
		
		
		this.worldDirectory = worldDirectory;
		layers = new ArrayList<LayerData>();
		
		if (!generate)
		{
			while (scanner.hasNext())
			{
				layers.add(new LayerData(this, scanner.next()));
			}
		}
		else
		{
			int numLayers = 10;
			for (int layer = 0; layer < numLayers; layer++)
			{
				layers.add(new LayerData(this,layer));
			}
			
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
