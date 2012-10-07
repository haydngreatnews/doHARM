package doharm.storage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class LayerData 
{
	private byte[][] tileIDs;
	
	public LayerData(WorldLoader worldLoader)
	{
		tileIDs = new byte[worldLoader.getNumTilesY()][worldLoader.getNumTilesX()];
	}
	
	/**
	 * GENERATES A RANDOM LEVEL (Just for testing purposes!)
	 * 
	 * @param worldLoader
	 * @param layerNumber
	 */
	public LayerData(WorldLoader worldLoader, int layerNumber)
	{
		this(worldLoader);
		
		for (int r = 0; r < worldLoader.getNumTilesY(); r++)
		{
			for (int c = 0; c < worldLoader.getNumTilesX(); c++)
			{
				
				if (layerNumber == 0)
				{
					if (r == 0 || c == 0 || r == worldLoader.getNumTilesY()-1 || c == worldLoader.getNumTilesX()-1)
					{
						tileIDs[r][c] = 0;
						continue;
					}
					else
						tileIDs[r][c] = 1;
					
				}
				else
				{
					if (worldLoader.getLayerData(layerNumber-1).getTileID(r, c) == 2)
					{
						tileIDs[r][c] = 2;
						continue;
					}
					double v = (1+Math.cos(r+c + r*c*Math.sin(c+r*c+c*r*r))/Math.PI)/2; //Math.random();
					if (v < 0.5f)
						tileIDs[r][c] = 2;
					else if (v < 0.8f)
						tileIDs[r][c] = 1;
					else if (v < 0.9f)
						tileIDs[r][c] = 3;
					else
						tileIDs[r][c] = 0;
					
				}
			}
		}
	}
	
	public LayerData(WorldLoader worldLoader, String fileName) throws IOException 
	{
		this(worldLoader);
		Scanner scanner = new Scanner(new File("res/worlds/"+worldLoader.getWorldDirectory()+"/layers/"+fileName));
		
		
		
		
		for (int r = 0; r < worldLoader.getNumTilesY(); r++)
		{
			String line = scanner.nextLine();
			for (int c = 0; c < worldLoader.getNumTilesX(); c++)
			{
				int read = line.charAt(c);
				byte id = (byte) (read - '0');
				
				
				tileIDs[r][c] = id;
			}
		}
		
	}
	
	
	

	public int getTileID(int y, int x) {
		return tileIDs[y][x];
	}
	
}
