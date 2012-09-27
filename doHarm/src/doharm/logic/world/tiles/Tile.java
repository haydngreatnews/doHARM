package doharm.logic.world.tiles;

import doharm.logic.physics.Vector;
import doharm.logic.world.Layer;
import doharm.storage.FloorTileData;

public class Tile implements Comparable<Tile>
{
	
	 
	public FloorTileData tileData;
	Vector position;
	private Layer layer;
	private int row;
	private int col;
	private int imageNumber;
	private int switchImageTimer;
	private int width;
	private int height;
	
	public Tile(Layer layer, int row, int col, int width, int height, Vector position, FloorTileData data) 
	{
		this.width = width;
		this.height = height;
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
	
	public int getMidX()
	{
		return position.getXAsInt()+width/2;
	}
	
	public int getMidY()
	{
		return position.getYAsInt()+height/2;
	}
	
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
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
	
	
	
	@Override
	public int compareTo(Tile t)
	{
		return 0;
	}
	
	
}
