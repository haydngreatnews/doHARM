package doharm.storage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class LayerData 
{
	private byte[][] tileIDs;
	public LayerData(WorldLoader worldLoader, String fileName) throws IOException 
	{
		Scanner scanner = new Scanner(new File("res/worlds/"+worldLoader.getWorldDirectory()+"/layers/"+fileName));
		
		
		tileIDs = new byte[worldLoader.getNumTilesY()][worldLoader.getNumTilesX()];
		
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
