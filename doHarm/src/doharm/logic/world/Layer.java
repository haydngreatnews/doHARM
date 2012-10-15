package doharm.logic.world;

import doharm.logic.physics.Vector;
import doharm.logic.world.tiles.Tile;
import doharm.storage.LayerData;
import doharm.storage.FloorTileData;
import doharm.storage.TilesetLoader;
import doharm.storage.WorldLoader;

public class Layer 
{
	private Tile[][] tiles;
	private int layerNumber;
	private int tileWidth;
	private int tileHeight;
	private World world;
	
	public Layer(World world, int layerNumber)
	{
		this.world = world;
		this.layerNumber = layerNumber;
		WorldLoader worldLoader = world.getWorldLoader();
		tiles = new Tile[worldLoader.getNumTilesY()][worldLoader.getNumTilesX()];
		TilesetLoader tileLoader = worldLoader.getTilesetLoader();
		LayerData layerData = worldLoader.getLayerData(layerNumber);
		
		
		
		this.tileWidth = tileLoader.getFloorTileWidth();
		this.tileHeight = tileLoader.getFloorTileHeight();
		
		for (int y= 0; y < tiles.length; y++)
		{
			for (int x= 0; x < tiles[0].length; x++)
			{
				FloorTileData data = tileLoader.getFloorTileData().get(layerData.getTileID(y,x));
						
				tiles[y][x] = new Tile(this, y,x,tileWidth,tileHeight,data, world.getColour(y, x, layerNumber));
			}
		}
	}
	
	
	public Tile getTileAt(float x, float y)
	{
		int c = (int)(x / tileWidth);
		int r = (int)(y / tileHeight);
		if (c < 0) c = 0;
		if (r < 0) r = 0;
		if (c >= tiles[0].length)
			c = tiles[0].length-1;
		if (r >= tiles[0].length)
			r = tiles[0].length-1;
	
		return tiles[r][c];
	}
	
	
	//tile_direction.png
	public Tile[][] getTiles()
	{
		return tiles;
	}
	
	public int getLayerNumber()
	{
		return layerNumber;
	}


	public World getWorld() 
	{
		return world;
	}
}
