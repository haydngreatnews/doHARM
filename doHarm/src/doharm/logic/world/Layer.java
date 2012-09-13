package doharm.logic.world;

import java.awt.Dimension;

import doharm.storage.TilesetLoader;
import doharm.storage.WorldLoader;

public class Layer 
{
	public Tile[][] tiles;
	
	public Layer(WorldLoader worldLoader, int layerNumber)
	{
		tiles = new Tile[worldLoader.getNumTilesX()][worldLoader.getNumTilesY()];
		TilesetLoader tileLoader = worldLoader.getTilesetLoader();
		
		for (int y= 0; y < tiles.length; y++)
		{
			for (int x= 0; x < tiles[0].length; x++)
			{
				int imageID = (x == 0 || y == 0 || x == tiles[0].length-1 || y == tiles.length-1)?0:1;
				tiles[x][y] = new Tile(new Dimension(x*tileLoader.getTileWidth(),y*tileLoader.getTileHeight()),imageID);
			}
		}
		
		
		
		
		
	}
	
	
	
	
	//tile_direction.png
	public Tile[][] getTiles()
	{
		return tiles;
	}
}
