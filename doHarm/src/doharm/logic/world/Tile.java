package doharm.logic.world;

import doharm.logic.physics.Vector;

public class Tile 
{
	public int imageID;
	Vector position;
	private Layer layer;
	private int row;
	private int col;
	
	public Tile(Layer layer, int row, int col, Vector position, int imageID) 
	{
		this.row = row;
		this.col = col;
		this.layer = layer;
		this.position = position;
		this.imageID = imageID;
	}

	public int getImageID() 
	{
		return imageID;
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
}
