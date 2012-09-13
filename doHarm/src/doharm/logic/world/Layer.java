package doharm.logic.world;

import java.awt.Dimension;

import doharm.storage.LayerData;
import doharm.storage.TilesetLoader;
import doharm.storage.WorldLoader;

public class Layer 
{
	public Tile[][] tiles;
	
	public Layer(WorldLoader worldLoader, int layerNumber)
	{
		tiles = new Tile[worldLoader.getNumTilesY()][worldLoader.getNumTilesX()];
		TilesetLoader tileLoader = worldLoader.getTilesetLoader();
		LayerData layerData = worldLoader.getLayerData(layerNumber);
		
		for (int y= 0; y < tiles.length; y++)
		{
			for (int x= 0; x < tiles[0].length; x++)
			{
				tiles[y][x] = new Tile(new Dimension(x*tileLoader.getTileWidth(),y*tileLoader.getTileHeight()),layerData.getImageID(y,x));
			}
		}
	}
	
	
	
	
	//tile_direction.png
	public Tile[][] getTiles()
	{
		return tiles;
	}
}
