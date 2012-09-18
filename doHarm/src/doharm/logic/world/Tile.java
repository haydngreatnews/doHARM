package doharm.logic.world;

import doharm.logic.physics.Vector;
import doharm.storage.TileData;

public class Tile 
{
	public TileData tileData;
	Vector position;
	private Layer layer;
	private int row;
	private int col;
	private int imageNumber;
	private int switchImageTimer;
	
	public Tile(Layer layer, int row, int col, Vector position, TileData data) 
	{
		this.row = row;
		this.col = col;
		this.layer = layer;
		this.position = position;
		this.tileData = data;
		switchImageTimer = tileData.getNumFramesPerImage();
	}

	public int getImageID() 
	{
		if (switchImageTimer == 0)
		{
			switchImageTimer = tileData.getNumFramesPerImage();
			imageNumber = (imageNumber + 1) % tileData.getNumImages();
		}
		else
			switchImageTimer--;
		
		
		return tileData.getImageID(imageNumber); //TODO
	}

	public int getX()
	{
		return position.getXAsInt();
	}
	public int getY()
	{
		return position.getYAsInt();
	}
	/*public Vector getPosition() 
	{
		return position;
	}*/
	
	public Layer getLayer()
	{
		return layer;
	}

	public int getRow() 
	{
		return row;
	}
	public int getCol() 
	{
		return col;
	}
	
	public boolean isWalkable()
	{
		return tileData.getType() != 0; //TODO
	}
}
