package doharm.logic.world.tiles;

import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.storage.FloorTileData;

public class RampTile extends Tile
{
	private Tile bottomTile; 
	private Tile topTile;
	public RampTile(Layer layer, int row, int col, int width, int height, FloorTileData data, int colour) 
	{
		super(layer, row, col, width, height, data, colour);
	}
	
	
}
